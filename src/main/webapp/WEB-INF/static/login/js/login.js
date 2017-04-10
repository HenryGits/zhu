/**
 * Created by Administrator on 2017/4/1.
 */
//定义全局变量
var userName;
var passWord;
var uFlag = false;
var pwdFlag = false;
//验证用户名
function checkName() {
    userName  = document.getElementById("name").value;
    if(userName == null || userName =="" || userName == undefined) {
        document.getElementById("name").style.border = "2px solid red";
        return uFlag;
    }
    document.getElementById("name").style.removeProperty("border");
    uFlag = true;
    return uFlag;
}

//验证密码
function checkPwd() {
    passWord  = document.getElementById("passWord").value;
    if(passWord == null || passWord =="" || passWord == undefined) {
        document.getElementById("passWord").style.border = "2px solid red";
        return pwdFlag;
    }
    document.getElementById("passWord").style.removeProperty("border");
    pwdFlag = true;
    return pwdFlag;
}

//登录
function loginUser() {
    checkName();
    checkPwd();
    if(uFlag == true && pwdFlag == true) {
        //ajax异步验证用户注册信息
        ajax({
            method: 'POST',
            url: 'doLogin1',
            data: {
                nickname: userName,
                pswd: passWord,
            },
            //如果返回success
            success: function (data) {
                if(data != null && data != ""){
                    window.location.href = "homePage/home";
                    return true;
                }
                alert("登录失败!!");
                window.location.href = "login";
                return false;
            }
        });

        //提交表单
        // document.getElementById("dis").onclick = function() {
        //     document.getElementById("loginFrom").submit();
        // }
    }
}

function reg() {
      //  document.getElementById("name").setAttribute("placeholder", "xxxxx");
    checkName();
    checkPwd();
    if(uFlag == true && pwdFlag == true) {
        //ajax异步验证用户注册信息
        //如果返回success
        //提交表单
        document.getElementById("dis").onclick = function() {
            document.getElementById("loginFrom").submit();
        }
    }
}