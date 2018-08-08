package com.cloudhelios.olympus.security.client.ip.whitelist.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author markfredchen
 * @date 2018/8/3
 */
@Data
@TableName("oauth_client_detail")
public class ClientDetail implements ClientDetails {
    @TableId(type = IdType.NONE)
    private String clientId;
    private String clientSecret;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    @TableField("resource_ids")
    private String resourceIdsString;
    @TableField("scopes")
    private String scopesString;
    @TableField("authorized_grant_types")
    private String authorizedGrantTypeString;
    @TableField("registered_redirect_uri")
    private String registeredRedirectUrisString;
    @TableField("authorities")
    private String authoritiesString;
    @TableField("auto_approve_scopes")
    private String autoApproveScopesString;
    @TableField("additional_information")
    private String additionalInformationString;
    private String ipWhitelist;

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @JsonIgnore
    @Override
    public Set<String> getResourceIds() {
        if (StringUtils.isEmpty(resourceIdsString)) {
            return new LinkedHashSet<>();
        }
        return Arrays.stream(resourceIdsString.split(",")).collect(Collectors.toSet());
    }

    public void setResourceIds(Set<String> resourceIds) {
        if (resourceIds == null) {
            this.resourceIdsString = null;
        } else {
            this.resourceIdsString = resourceIds.stream().collect(Collectors.joining(","));
        }
    }

    @JsonIgnore
    @Override
    public Set<String> getScope() {
        if (StringUtils.isEmpty(scopesString)) {
            return new LinkedHashSet<>();
        }
        return Arrays.stream(scopesString.split(",")).collect(Collectors.toSet());
    }

    public void setScope(Set<String> scopes) {
        if (scopes == null) {
            this.scopesString = null;
        } else {
            this.scopesString = scopes.stream().collect(Collectors.joining(","));
        }
    }

    @JsonIgnore
    @Override
    public boolean isScoped() {
        return this.getScope() != null && !this.getScope().isEmpty();
    }

    @JsonIgnore
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        if (StringUtils.isEmpty(authorizedGrantTypeString)) {
            return new LinkedHashSet<>();
        }
        return Arrays.stream(authorizedGrantTypeString.split(",")).collect(Collectors.toSet());
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        if (authorizedGrantTypes == null) {
            this.authorizedGrantTypeString = null;
        } else {
            this.authorizedGrantTypeString = authorizedGrantTypes.stream().collect(Collectors.joining(","));
        }
    }

    @JsonIgnore
    @Override
    public Set<String> getRegisteredRedirectUri() {
        if (StringUtils.isEmpty(registeredRedirectUrisString)) {
            return new LinkedHashSet<>();
        } else {
            return Arrays.stream(registeredRedirectUrisString.split(",")).collect(Collectors.toSet());
        }
    }

    public void setRegisteredRedirectUri(Set<String> registeredRedirectUris) {
        if (registeredRedirectUris == null) {
            this.registeredRedirectUrisString = null;
        } else {
            this.registeredRedirectUrisString = registeredRedirectUris.stream().collect(Collectors.joining(","));
        }
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if (StringUtils.isEmpty(this.authoritiesString)) {
            return new LinkedHashSet<>();
        } else {
            return Arrays.stream(authoritiesString.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
    }

    public void setAuthorities(Set<String> authorities) {
        if (authorities == null) {
            this.authoritiesString = null;
        } else {
            this.authoritiesString = authorities.stream().collect(Collectors.joining(","));
        }
    }

    @JsonIgnore
    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @JsonIgnore
    public Set<String> getAutoApproveScopes() {
        if (StringUtils.isEmpty(this.autoApproveScopesString)) {
            return new LinkedHashSet<>();
        } else {
            return Arrays.stream(autoApproveScopesString.split(",")).collect(Collectors.toSet());
        }
    }

    public void setAutoApproveScopes(Set<String> autoApproveScopes) {
        if (autoApproveScopes == null) {
            this.autoApproveScopesString = null;
        } else {
            this.autoApproveScopesString = autoApproveScopes.stream().collect(Collectors.joining(","));
        }
    }

    @JsonIgnore
    @Override
    public Map<String, Object> getAdditionalInformation() {
        if (StringUtils.isEmpty(this.additionalInformationString)) {
            return new LinkedHashMap<>();
        } else {
            Map<String, Object> result = new LinkedHashMap<>();
            JSONObject additionalInformationJSONObject = JSON.parseObject(this.additionalInformationString);
            additionalInformationJSONObject.keySet().forEach(key -> {
                result.put(key, additionalInformationJSONObject.get(key));
            });
            return result;
        }
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        if (additionalInformation == null) {
            this.additionalInformationString = null;
        } else {
            this.additionalInformationString = JSON.toJSONString(additionalInformation);
        }
    }
}
