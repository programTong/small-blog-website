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
        childRouter: ["/status", "/article_manage","/article_publish","/category_manage","/blog_sea","/user_view"],
        childContent: ["仪表盘", "文章管理","发布文章","分类管理","博客洋","我的"]
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
