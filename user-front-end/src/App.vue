<template>
  <div>
    <App_Header/>
    <router-view name="app_user"/>
    <App_Menu/>
    <div id="body">
      <router-view name="body_router_view"/>
    </div>
  </div>
</template>

<script>
import Request from "./js/Request";
import App_Header from './components/App_Header.vue'
import App_Menu from './components/App_Menu.vue'
Request.addParam("username","zhangsan6");
Request.get("http://localhost:8080/user/findByUserName")
    .then(response =>{
      return response.json()
    })
    .then(json => {
      console.log("aaa:"+json);
    }).catch(e => {
      console.log(e);
    })
export default {
  name: 'App',
  data(){
    return {
      user: {}
    }
  },
  components: {
    App_Header,App_Menu
  },
  methods: {

  }
}
</script>

<style>
#body{
  margin-left: 150px;
  margin-top: -100px;
}
</style>
