<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password','password-confirm'); section>
    <#if section = "header">
        Создать аккаунт
    <#elseif section = "form">
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <form id="kc-register-form" action="${url.registrationAction}" method="post">
                    <div class="${properties.kcFormGroupClass!}">
                        <label for="username" class="${properties.kcLabelClass!}">${msg("username")}</label>
                        <input id="username" name="username" type="text" value="${(register.formData.username!'')}"
                               autocomplete="username" class="${properties.kcInputClass!}"
                               aria-invalid="<#if messagesPerField.existsError('username')>true</#if>" required>
                        <#if messagesPerField.existsError('username')>
                            <span class="${properties.kcInputErrorMessageClass!}">${kcSanitize(messagesPerField.get('username'))?no_esc}</span>
                        </#if>
                    </div>
                    <div class="${properties.kcFormGroupClass!}">
                        <label for="password" class="${properties.kcLabelClass!}">${msg("password")}</label>
                        <input id="password" name="password" type="password" autocomplete="new-password"
                               class="${properties.kcInputClass!}"
                               aria-invalid="<#if messagesPerField.existsError('password')>true</#if>" required>
                        <#if messagesPerField.existsError('password')>
                            <span class="${properties.kcInputErrorMessageClass!}">${kcSanitize(messagesPerField.get('password'))?no_esc}</span>
                        </#if>
                    </div>
                    <div class="${properties.kcFormGroupClass!}">
                        <label for="password-confirm" class="${properties.kcLabelClass!}">${msg("passwordConfirm")}</label>
                        <input id="password-confirm" name="password-confirm" type="password" autocomplete="new-password"
                               class="${properties.kcInputClass!}"
                               aria-invalid="<#if messagesPerField.existsError('password-confirm')>true</#if>" required>
                        <#if messagesPerField.existsError('password-confirm')>
                            <span class="${properties.kcInputErrorMessageClass!}">${kcSanitize(messagesPerField.get('password-confirm'))?no_esc}</span>
                        </#if>
                    </div>
                    <div class="${properties.kcFormGroupClass!} ${properties.kcFormSettingClass!}">
                        <button class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!}"
                                type="submit" name="register">${msg("doRegister")}</button>
                    </div>
                </form>
            </div>
        </div>
    <#elseif section = "info">
        <div id="kc-registration">
            <span>Уже есть аккаунт? <a href="${url.loginUrl}">Войти</a></span>
        </div>
    </#if>
</@layout.registrationLayout>
