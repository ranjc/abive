//login
abive.passport = {};

abive.passport.data = {
    account:null,
    password:null
}

abive.passport.settings = {
    account_reg : /^[a-zA-Z0-9_]{3,16}$/,
    password_reg : /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,22}$/,
    rsa_public_module : '',
    rsa_public_exponent : '',
    forward : '',
    tip_account : '请输入正确的账号',
    tip_password : '请输入正确的密码',
    tip_fail : '登录失败'
}

abive.passport.init = function(){
    $("#inputAccount").bind('change',function(){abive.passport.onAccountChange()});
    $("#inputPassword").bind('change',function(){abive.passport.onPasswordChange()});
    $("#login-do").one('click',function(){abive.passport.do()})

    this.settings.rsa_public_module = $("#passportKey").attr("modulus").toString();
    this.settings.rsa_public_exponent = $("#passportKey").attr("exponent").toString();

    this.settings.forward = $("#passportKey").attr("forward").toString();
    if(this.settings.forward.substring(4) != "http"){
        this.settings.forward = "http://" + this.settings.forward;
    }
};

abive.passport.getInput = function(){
    var data = {};
    $.extend(data , this.data);

    data.account = $.trim($("#inputAccount").val());
    data.password = $.trim($("#inputPassword").val());

    return data;
};

//数据校验
abive.passport.checkAccount = function(data){
    return this.settings.account_reg.test(data.account);
}

abive.passport.checkPassword = function(data){
    return this.settings.password_reg.test(data.password);
}

abive.passport.checkInput = function(data){
    return this.checkAccount(data) && abive.passport.checkPassword(data);
}

//输入时检测
abive.passport.onAccountChange = function(){
    var data = this.getInput();
    if (!this.checkAccount(data)){
        $("#loginTip").removeClass("hidden").find("#loginTipInfo").text(this.settings.tip_account);
    }else{
        $("#loginTip").addClass("hidden");
    }
}

abive.passport.onPasswordChange = function(){
    var data = this.getInput();
    if (!this.checkPassword(data)){
        $("#loginTip").removeClass("hidden").find("#loginTipInfo").text(this.settings.tip_password);
    }else{
        $("#loginTip").addClass("hidden");
    }
}

abive.passport.do = function(){
    var data = this.getInput();

    if(!this.checkAccount(data)){
        $("#login-do").one('click',function(){abive.passport.do()})
        $("#loginTip").removeClass("hidden").find("#loginTipInfo").text(this.settings.tip_account);
    }else if(!this.checkPassword(data)){
        $("#login-do").one('click',function(){abive.passport.do()})
        $("#loginTip").removeClass("hidden").find("#loginTipInfo").text(this.settings.tip_password);
    }else{
        data.password = this.getSafePassword(data.password);

        $.ajax({
            type:"POST",
            url:"/login/do.json",
            data : data,
            success : function(result){
                if (result.code == 0) {
                    window.location = abive.passport.settings.forward;
                }else if (result.code == 1){
                    $("#login-do").one('click',function(){abive.passport.do()})
                    $("#loginTip").removeClass("hidden").find("#loginTipInfo").text(result.tip);
                }else {
                    $("#login-do").one('click',function(){abive.passport.do()})
                    $("#loginTip").removeClass("hidden").find("#loginTipInfo").text(this.settings.tip_fail);
                }
            },
            error : function(){
                $("#login-do").one('click',function(){abive.passport.do()})
                $("#loginTip").removeClass("hidden").find("#loginTipInfo").text(this.settings.tip_fail);
            }
        })
    }
};

abive.passport.getSafePassword = function(password){
    var key = RSAUtils.getKeyPair(this.settings.rsa_public_exponent, '', this.settings.rsa_public_module);
    return RSAUtils.encryptedString(key, $.md5(password));
};

//执行初始化
$(function(){
        abive.passport.init();
    }
);