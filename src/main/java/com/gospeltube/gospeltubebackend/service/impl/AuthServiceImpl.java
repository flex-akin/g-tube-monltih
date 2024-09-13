package com.gospeltube.gospeltubebackend.service.impl;

import com.gospeltube.gospeltubebackend.dto.AppUserDto;
import com.gospeltube.gospeltubebackend.dto.ChurchSignUpDto;
import com.gospeltube.gospeltubebackend.dto.LoginResponseDto;
import com.gospeltube.gospeltubebackend.dto.SignUpDto;
import com.gospeltube.gospeltubebackend.entity.AppUser;
import com.gospeltube.gospeltubebackend.entity.Church;
import com.gospeltube.gospeltubebackend.entity.EmailVerification;
import com.gospeltube.gospeltubebackend.entity.Role;
import com.gospeltube.gospeltubebackend.exception.BadRequestException;
import com.gospeltube.gospeltubebackend.exception.DuplicateException;
import com.gospeltube.gospeltubebackend.exception.ResourceNotFoundException;
import com.gospeltube.gospeltubebackend.exception.UndefinedRoleException;
import com.gospeltube.gospeltubebackend.mapper.UserMapper;
import com.gospeltube.gospeltubebackend.repository.ChurchRepository;
import com.gospeltube.gospeltubebackend.repository.EmailVerificationRepository;
import com.gospeltube.gospeltubebackend.repository.RoleRepository;
import com.gospeltube.gospeltubebackend.repository.UserRepository;
import com.gospeltube.gospeltubebackend.service.AuthService;
import com.gospeltube.gospeltubebackend.service.EmailService;
import com.gospeltube.gospeltubebackend.service.TokenService;
import com.gospeltube.gospeltubebackend.util.Utilities;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final EmailVerificationRepository emailVerificationRepository;
    private final ChurchRepository churchRepository;


    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService, EmailService emailService, EmailVerificationRepository emailVerificationRepository, ChurchRepository churchRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.emailVerificationRepository = emailVerificationRepository;
        this.churchRepository = churchRepository;
    }

    @Override
    public AppUserDto createAppUser(SignUpDto signUpDto) {
        Optional<AppUser> appUser = this.userRepository.findByEmail(signUpDto.getEmail());
        if (appUser.isPresent()){
            if (signUpDto.isOauth2()){
                throw new BadRequestException("oauth user already exist");
            }
            throw new DuplicateException("already exist");
        }


        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        encodedPassword = "{bcrypt}" + encodedPassword;
        Optional<Role> value = this.roleRepository.findByAuthority("USER");
        Role userRole = null;
        if (value.isPresent()) {
            userRole = value.get();
        }
        else {
            throw new UndefinedRoleException("Roles have not been defined");
        }
        String streamId = Utilities.generateStreamName(signUpDto.getLastName());
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        AppUser savedUser = this.userRepository.save(new AppUser(
                signUpDto.getPhoneNumber(),
                signUpDto.getEmail(),
                encodedPassword,
                signUpDto.getFirstName(),
                signUpDto.getLastName(),
                signUpDto.getGender(),
                null,
                authorities,
                null,
                streamId,
                null,
                true,
                signUpDto.getProfilePicture(),
                signUpDto.isOauth2()
        ));

        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public AppUserDto createChurch(ChurchSignUpDto churchSignUpDto) {
        Optional<AppUser> appUser = this.userRepository.findByEmail(churchSignUpDto.getEmail());
        if (appUser.isPresent()){
            throw new DuplicateException("this email is already used");
        }
        String encodedPassword = passwordEncoder.encode(churchSignUpDto.getPassword());
        encodedPassword = "{bcrypt}" + encodedPassword;
        Optional<Role> value = this.roleRepository.findByAuthority("CHURCH");
        Role userRole;
        if (value.isEmpty()) throw new UndefinedRoleException("Roles have not been defined");
        userRole = value.get();
        Set<Role> authorities = new HashSet<>();
        String gender = "C";
        authorities.add(userRole);
        String streamId = Utilities.generateStreamName(churchSignUpDto.getFirstName());
        Church savedChurch = this.churchRepository.save(new Church(
                churchSignUpDto.getDescription(),
                churchSignUpDto.getWebsite(),
                churchSignUpDto.getFirstName(),
                churchSignUpDto.getAddress(),
                churchSignUpDto.getProvince(),
                churchSignUpDto.getCity(),
                churchSignUpDto.getCountry(),
                churchSignUpDto.getZip(),
                churchSignUpDto.getLogo(),
                churchSignUpDto.getDoc()
        ));
        AppUser savedUser = this.userRepository.save(new AppUser(
                null,
                churchSignUpDto.getEmail(),
                encodedPassword,
                churchSignUpDto.getFirstName(),
                churchSignUpDto.getLastName(),
                gender,
                null,
                authorities,
                savedChurch,
                streamId,
                streamId + "_EXT",
                true,
                null,
                churchSignUpDto.isOauth2()
        ));


        return UserMapper.mapToUserDto(savedUser);
    }


    @Override
    public boolean emailVerification(String email, String code) {
        Optional<EmailVerification> value = this.emailVerificationRepository.findByEmail(email);
        EmailVerification userEmail;

        if (value.isPresent()){
            userEmail = value.get();
        }
        else {
            throw new ResourceNotFoundException("user not found");
        }

        if (Objects.equals(userEmail.getCode(), code)){
            return true;
        }
        else {
            throw new BadCredentialsException("incorrect code");
        }
    }

    public LoginResponseDto loginUser(String email, String password, boolean isOauth){
        try{
            AppUser appUser = this.userRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("user not found")
            );

            if (appUser.isOauth2()){

            }
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            String token = tokenService.generateJwt(auth);


            AppUserDto appUserDto = UserMapper.mapToUserDto(appUser);
            return new LoginResponseDto(appUserDto, token);
        }catch (AuthenticationException e){
            throw new com.gospeltube.gospeltubebackend.exception.AuthenticationException(e.getMessage());
        }
    }

    public boolean sendOtp(String email) throws Exception {
        Optional<AppUser> value = this.userRepository.findByEmail(email);
        if(value.isPresent()){
            throw new DuplicateException("user already exist");
        }
        Optional<EmailVerification> emailUser = this.emailVerificationRepository.findByEmail(email);
        String code = Utilities.generateOTP();
        if(emailUser.isPresent()){

            EmailVerification existingEmailVerification = emailUser.get();
            existingEmailVerification.setCode(code);
            emailService.sendEmail(email, code);
            this.emailVerificationRepository.save(existingEmailVerification);
            return true;
        }

        emailService.sendEmail(email, code);
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setCode(code);
        this.emailVerificationRepository.save(emailVerification);
        return true;
    }

    @Override
    public AppUserDto updateUserDp(String email, String logoUrl) {
        AppUser appUser = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        appUser.setProfilePicture(logoUrl);
        this.userRepository.save(appUser);
        return UserMapper.mapToUserDto(this.userRepository.save(appUser));
    }

    @Override
    public Church updateCoyLogo(String email, String logoUrl) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("church doesn't exit")
        );
        church.setLogo(logoUrl);
        user.setProfilePicture(logoUrl);
        this.userRepository.save(user);
        return this.churchRepository.save(church);
    }

    @Override
    public Church updateCoyDoc(String email, String docUrl) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("church doesn't exit")
        );
        church.setLogo(docUrl);
        return this.churchRepository.save(church);
    }

    public String forgotPassword(String email) throws Exception {
        this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("invalid email")
        );
        this.sendOtp(email);
        return "reset password email sent";
    }

    public String changePassword (String oldPassword, String newPassword, String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(" invalid email")
        );
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, oldPassword)
        );
        String encodedPassword = passwordEncoder.encode(newPassword);
        encodedPassword = "{bcrypt}" + encodedPassword;
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "password changed";
    }

    public String resetForgotPassword(String email, String code, String password) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("invalid email")
        );
        this.emailVerification(email, code);
        String encodedPassword = passwordEncoder.encode(password);
        encodedPassword = "{bcrypt}" + encodedPassword;
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "password reset complete";
    }
}
