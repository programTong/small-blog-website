<template>
  <div>
    <div>
      <span v-for="(content,i) in childContent" @click="menuClick(i)" style="margin: 0px 3px">{{content}}</span>
    </div>
    <router-view name="sea_rv"/>
  </div>
</template>

<script>
export default {
  name: "Blog_Sea",
  data(){
    return {
      childRouter: ["/blog_sea/latest_article", "/blog_sea/search_article"],
      childContent: ["最新文章", "文章搜索"]
    }
  },
  methods: {
    menuClick: function (i){
      i = parseInt(i);
      this.$router.replace({
        path: this.childRouter[i]
      })
    },

  }
}
</script>

<style scoped>

</style>
