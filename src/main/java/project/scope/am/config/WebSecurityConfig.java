package project.scope.am.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.scope.am.security.UserDetailServiceImpl;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/perform_login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/successLogin")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/teamLider").hasAnyAuthority("TEAM_LEADER")
                .antMatchers("/users").hasAnyAuthority("TEAM_LEADER")
                .antMatchers("/projects").hasAnyAuthority("TEAM_LEADER")
                .antMatchers("/newProjectPage").hasAnyAuthority("TEAM_LEADER")
                .antMatchers("/userProjects").hasAnyAuthority("TEAM_MEMBER")
                .antMatchers("/logs").hasAnyAuthority("TEAM_MEMBER")
                .antMatchers("/addLogPage").hasAnyAuthority("TEAM_MEMBER")
                .antMatchers("/").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
