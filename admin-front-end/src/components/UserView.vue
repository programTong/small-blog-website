<template>
  <div>
    <div>{{user.username}}</div>
    <div><input type="button" @click="logOut" value="退出"></div>
  </div>
</template>

<script>
import GlobalVar from "../js/GlobalVar";
export default {
  name: "UserView",
  data(){
    return {
      user: {}
    }
  },
  methods: {
    logOut: function (){
      GlobalVar.user = undefined;
      GlobalVar.user_auth_data = undefined;
      alert("已退出");
      location.href="http://localhost:8082";
    }
  },
  beforeRouteEnter(to,from,next){
    next(vm => {
      vm.user=GlobalVar.user;
    })
  }
}
</script>

<style scoped>

</style>
