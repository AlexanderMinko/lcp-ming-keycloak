<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=false displayMessage=false; section>
<#if section = "title">
    ${msg("loginTitle",(realm.displayName!''))}
<#elseif section = "form">
    <#if realm.password>
        <div class="container" style="margin-top: 8rem">
            <div class="row">
                <div class="col-lg-4 col-md-4 mx-auto">
                    <div class="card">
                        <div class="card-header">
                            Sign in to Socket
                            <#if realm.resetPasswordAllowed>
                                <div style="float:right; font-size: 80%; position: relative; top:-10px"><a
                                            href="${url.loginResetCredentialsUrl}">${msg("doForgotPassword")}</a>
                                </div>
                            </#if>
                        </div>
                        <div class="card-body">
                            <#if message?has_content>
                                <div id="login-alert" class="alert alert-danger col-sm-12">
                                    <span class="kc-feedback-text">${kcSanitize(message.summary)?no_esc}</span>
                                </div>
                            </#if>
                            <form id="kc-form-login" onsubmit="login.disabled = true; return true;"
                                  action="${url.loginAction?keep_after('^[^#]*?://.*?[^/]*', 'r')}" method="post">

                                <div class="form-group">
                                    <label>Username <span class="text-danger">*</span></label>
                                    <#if usernameEditDisabled??>
                                        <input tabindex="1" id="username" class="${properties.kcInputClass!}"
                                               name="username" value="${(login.username!'')}" type="text" disabled
                                               placeholder="<#if !realm.loginWithEmailAllowed>${msg("username")}
                                               <#elseif !realm.registrationEmailAsUsername>${msg("usernameOrEmail")}<#else>${msg("email")}</#if>"/>
                                    <#else>
                                        <input tabindex="1" id="username" class="${properties.kcInputClass!}"
                                               name="username" value="${(login.username!'')}" type="text" autofocus
                                               autocomplete="off"
                                               placeholder="<#if !realm.loginWithEmailAllowed>${msg("username")}<#elseif !realm.registrationEmailAsUsername>${msg("usernameOrEmail")}<#else>${msg("email")}</#if>"/>
                                    </#if>
                                </div>
                                <div class="form-group">
                                    <label>Password <span class="text-danger">*</span></label>
                                    <input tabindex="2" id="password" class="${properties.kcInputClass!}"
                                           name="password" type="password" autocomplete="off"
                                           placeholder="${msg("password")}"/>
                                </div>
                                <div id="kc-form-options" class="form-group">
                                    <#if realm.rememberMe && !usernameEditDisabled??>
                                        <div class="custom-control custom-checkbox">
                                            <#if login.rememberMe??>
                                                <input tabindex="3" id="rememberMe" name="rememberMe"
                                                       type="checkbox" class="custom-control-input" tabindex="3"
                                                       checked> ${msg("rememberMe")}
                                            <#else>
                                                <input tabindex="3" id="rememberMe" name="rememberMe"
                                                       type="checkbox" class="custom-control-input"
                                                       tabindex="3"> ${msg("rememberMe")}
                                            </#if>
                                        </div>
                                        <label class="custom-control-label" for="customControlAutosizing">Remember
                                            Me</label>
                                    </#if>
                                </div>
                                <button type="submit" class="btn btn-dark btn-block">SIGN IN</button>
                            </form>
                            <#if realm.password && social.providers??>
                                <div class="mt-4 text-center login-with-social">
                                    <#list social.providers as p>
                                        <a href="${p.loginUrl}" id="zocial-${p.alias}"
                                           class="
                                         <#if p.displayName== "Google"> btn btn-outline-danger btn-block
                                         <#elseif p.displayName == "Facebook"> btn btn-outline-primary btn-block
                                         <#else> btn btn-outline-info btn-block
                                         </#if>">
                                            <#if p.iconClasses?has_content>
                                                <i class="${p.iconClasses!}" aria-hidden="true"></i>
                                            </#if>
                                            ${msg("doLogIn")} With ${p.displayName}
                                        </a>
                                    </#list>
                                </div>
                            </#if>

                            <#if realm.password && realm.registrationAllowed && !usernameEditDisabled??>
                                <div class="form-group">
                                    <div class="col-md-12 control">
                                        <div style="border-top: 1px solid#888; padding-top:15px;">
                                            ${msg("noAccount")}
                                            <a tabindex="6" href="${url.registrationUrl}"
                                               style="font-weight: bold;">
                                                ${msg("doRegister")}
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </#if>
</#if>
</@layout.registrationLayout>
