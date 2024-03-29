package ca.est.security;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ca.est.entity.UserCMS;
import ca.est.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Estevam Meneses
 */
@Getter
@Setter
public class UserDetailsBuilder implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	Long id_user;
	String username;
	String password;
	String token;
	LocalDateTime created;
	Collection<? extends GrantedAuthority> authorities = null;
	UserCMS userCMS;
	public UserDetailsBuilder() {

	}
    public UserDetailsBuilder(Long id_user, String username,String password,LocalDateTime created,
			Set<GrantedAuthority> authorities,UserCMS userCMS) {
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.created = created;
		this.authorities = authorities;
		this.userCMS= userCMS;
	}
	/**
     * @param userCMS
     * @return
     */
    public static UserDetailsBuilder build(UserCMS userCMS) {
    	//Set<UserRole> userRolesList = userBlog.getUserRole();
    	//Set<String> rolesList = userRolesList.stream().map(UserRole::getRole).collect(Collectors.toSet());
    	UserRole userRole = userCMS.getUserRole();
    	Set<String> rolesList = new HashSet<String>();
    	rolesList.add(userRole.getRole());
    	return new UserDetailsBuilder(
    			userCMS.getId_user(),
    			userCMS.getUsername(),
    			userCMS.getPassword(),
    			userCMS.getCreated(),
                mapToGrantedAuthorities(rolesList),
                userCMS
        );
    }

    
    /**
     * @param Task code
     * GrantedAuthority is used by the spring security 
     * @return
     */
    private static Set<GrantedAuthority> mapToGrantedAuthorities(Set<String> rolesList) {
        return rolesList.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toSet());
    }


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}