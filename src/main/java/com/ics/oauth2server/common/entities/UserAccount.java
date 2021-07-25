package com.ics.oauth2server.common.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_accounts")
public class UserAccount extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_acc_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(strategy = "native", name = "native")
    private long id;
    private long userId;
    @Column(unique = true)
    private String email;
    @Column(unique = true,updatable = false)
    private String username;
    private String password;
    private boolean enabled;
    private int failedLoginAttempts;
    //private Date lastLoginTime;
    private String phoneNo;
    private int loginCount;
    private boolean accountVerified;
    private boolean isFlag;

    @OneToMany(mappedBy = "userAccount")
    private List<SecureToken> secureToken;

    @Column(name = "account_locked")
    private boolean accountNonLocked;

    @Column(name = "account_expired")
    private boolean accountNonExpired;

    @Column(name = "credentials_expired")
    private boolean credentialsNonExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user", joinColumns = {
            @JoinColumn(name = "user_acc_id", referencedColumnName = "user_acc_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "role_id") })
    private List<Roles> roles;

    @OneToMany(mappedBy = "userAccount",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<AdditionalRoles> additionalRoles;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public  void setIsEnabled(boolean enabled) {
        this.enabled =  enabled;
    }
    @Override
    public boolean isAccountNonExpired() {
        return !accountNonExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountNonLocked;
    }

    /*
     * Get roles and permissions and add them as a Set of GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//        roles.forEach(r -> {
//            authorities.add(new SimpleGrantedAuthority(r.getName()));
//            r.getPermissions().forEach(p -> {
//                //TODO: Need to add the dynamic permission.
//                authorities.add(new SimpleGrantedAuthority(p.getName()));
//            });
//        });
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
      this.password=password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<Roles> getRoles(){
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public boolean isAccountVerified() {
        return accountVerified;
    }

    public void setAccountVerified(boolean accountVerified) {
        this.accountVerified = accountVerified;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public List<SecureToken> getSecureToken() {
        return secureToken;
    }

    public void setSecureToken(List<SecureToken> secureToken) {
        this.secureToken = secureToken;
    }

    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public List<AdditionalRoles> getAdditionalRoles() {
        return additionalRoles;
    }

    public void setAdditionalRoles(List<AdditionalRoles> additionalRoles) {
        this.additionalRoles = additionalRoles;
    }
}
