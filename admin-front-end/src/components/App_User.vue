<template>
  <div>
    <div style="font-size: 30px">{{user.username}}</div>
  </div>
</template>

<script>
import GlobalVar from "../js/GlobalVar";
export default {
  name: "App_User",
  data(){
    return {
      user: {}
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
