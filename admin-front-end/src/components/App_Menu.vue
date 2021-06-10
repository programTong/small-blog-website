<template>
  <div id="menu_div_ofwenr">
    <div v-for="(content,i) in childContent" @click="menuClick(i)">{{content}}</div>
  </div>
</template>

<script>
export default {
  name: "App_Menu",
  data(){
    return {
        childRouter: ["/status", "/article_manage","/category_manage","/comment_manage","/user_view"],
        childContent: ["仪表盘", "文章管理","分类管理","评论管理","我的"]
    }
  },
  methods: {
    menuClick: function (i){
      i = parseInt(i);
      this.$router.replace({
        path: this.childRouter[i]
      })
    },
  },

}
</script>

<style scoped>
  #menu_div_ofwenr{
    width: 100px;
  }
</style>
