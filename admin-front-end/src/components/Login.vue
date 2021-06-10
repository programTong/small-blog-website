<template>
  <div>
    <table>
      <tr>
        <td>用户名</td>
        <td><input type="text" name="username" v-model="username" @blur="checkUsername"></td>
        <td><span>{{username_status}}</span></td>
      </tr>
      <tr>
        <td>密码</td>
        <td><input type="password" name="password" v-model="password" @blur="checkPassword"></td>
        <td><span>{{password_status}}</span></td>
      </tr>
      <tr>
        <td>验证码</td>
        <td><input type="text" name="authcode" v-model="authcode" @blur="checkAuthcode"></td>
        <td><span>{{authcode_status}}</span></td>
      </tr>
      <tr>
        <td><input type="button" value="登录" disabled id="submit_p_login102" @click="submitJson"></td>
      </tr>
    </table>
    <div>{{login_msg}}</div>
    <div>
      <img id="img_td_weoufsjrlweru" v-bind:src="auth_code_pic_src" v-on:click="refreshAuthCode">
      <span v-on:click="refreshAuthCode">刷新</span>
    </div>
  </div>
</template>

<script>
import GlobalVar from "../js/GlobalVar";
export default {
  name: "Login",
  data() {
    return {
      host: "http://localhost:8080",
      appContext: "http://localhost:8080/admin",
      username: "",
      username_existed: "",
      username_status: "字母开头，允许5-16字，允许字母数字下划线",
      password: "",
      password_status: "以字母开头，长度在6~18之间，只能包含字母、数字和下划线",
      authcode: "",
      authcode_status: "",
      authcode_corrected: "",
      auth_code_pic_src: "",
      submitable: [0,0,0],
      login_msg: "",
    }
  },
  methods: {
    submitJson: async function () {
        fetch( this.appContext + "/login", {
          method: 'post',
          headers: {
            'content-type': 'application/json'
          },
          body: JSON.stringify({
            id: null,
            username: this.username,
            password: this.password,
          }),
          mode: 'cors',
        }).then(response =>{
          return response.json()
        }).then((json) => {
          if (json.flag === true) {
            this.login_msg = "登录成功!!!";
            let user_auth_data = {
                token: json.data.token,
                uid: json.data.aid,
                role: json.data.role,
                username: json.data.username,
              }
            GlobalVar.user=json.data.admin;
            GlobalVar.user_auth_data=user_auth_data;
            let router = this.$router;
            setTimeout(function (){
              router.replace({
                path: '/status'
              })
            },3000);
          } else if (json.flag === false) {
            this.login_msg = json.message;
          } else {
            this.login_msg = "登录失败!!!";
            console.log("json数据异常");
          }
        })
    },
    refreshAuthCode: function() {
      let base = this.host+"/authCode?";
      this.auth_code_pic_src = base + new Date().getTime();
      console.log("aaaa");
    },
    checkUsername: async function () {
        fetch( this.appContext + "/findByUserName?username="+this.username, {
          method: 'get',
          headers: {
            'content-type': 'application/json'
          },
          mode: 'cors'
        }).then(response =>{
          return response.json()
        }).then(json => {
          this.username_existed = json.flag;
          if (this.username_existed === false) {
            this.username_status = "用户名不存在";
            this.submitable[0] = 0;
          } else {
            this.username_status = "用户名存在";
            this.submitable[0] = 1;
          }
          this.checkInput();
        }).catch(e => {
          console.log(e);
        })

    },
    checkPassword: function () {
//检查密码
      if (/\w{1,16}/.test(this.password)) {
        this.password_status = "密码合法";
        this.submitable[1] = 1;
      } else {
        this.password_status = "密码不合法 (以字母开头，长度在6~18之间，只能包含字母、数字和下划线)";
        this.submitable[1] = 0;
      }
      this.checkInput();

    },
    checkAuthcode: async function () {
      fetch(  this.host+"/checkAuthcode/"+this.authcode, {
        method: 'get',
        headers: {
          'content-type': 'application/json'
        },
        mode: 'cors'
      }).then(response =>{
        return response.json()
      }).then(json => {
        this.authcode_corrected = json.data.authcode_corrected;
        //检查验证码
        if (/\w{4}/.test(this.authcode)) {
          if (this.authcode_corrected === true) {
            this.authcode_status = "验证码正确";
            this.submitable[2] = 1;
          } else {
            this.authcode_status = "验证码错误";
            this.submitable[2] = 0;
          }
        } else {
          this.submitable[2] = 0;
          this.authcode_status = "验证码不合法";
        }
        this.checkInput();
      }).catch(e => {
        console.log(e);
      })

    },
    checkInput: function () {
      console.log(this.submitable);
      let result = 1;
      for (let i = 0; i < this.submitable.length; i++) {
        result = result * this.submitable[i];
      }
      console.log("result:" +result);
      let submit_p = document.getElementById("submit_p_login102");
      if (result === 1) {
        submit_p.removeAttribute("disabled");
      } else {
        submit_p.setAttribute("disabled", "");
      }

    },

  },
  mounted() {
    this.refreshAuthCode();
  },
}
</script>

<style scoped>
#img_td_weoufsjrlweru{
  width: 50px;
  height: 50px;

}
</style>
