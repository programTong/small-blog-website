<template>
  <div>
    <div style="font-size: 30px">{{article.title}}</div>
    <div>{{user.username}}</div>
    <div>
      <span  v-for="tag in article.tags"><span style="margin: 0px 3px;border: 1px solid black">{{tag}}</span></span>
    </div>
    <div>
      <span v-for="category in article.categories">
        <span style="margin: 0px 3px;background-color: dodgerblue">{{category}}</span>
      </span>
    </div>
    <div style="margin-top: 10px;border: 2px solid black;width: 500px;height: 500px">{{article.content}}</div>
    <router-view name="article_comment"/>
  </div>
</template>

<script>

import GlobalVar from "../js/GlobalVar";
export default {
  name: "Article_View",
  data(){
    return {
      article: {
      },
      user: {}
    }
  },
  components:{
    Comment
  },
  methods: {
    loadData: function (uid){
      fetch('http://localhost:8080/article/user?uid='+uid,{
        method: 'get',
        headers: {
          "Authorization": GlobalVar.user_auth_data.role+" "+GlobalVar.user_auth_data.token
        },
        mode: 'cors'
      }).then(response => {
        return response.json()
      }).then(json => {
        console.log(json);
        if (json.flag === true){
          this.user = json.data;
        }else{
          alert("用户信息加载失败");
        }
      }).catch(e => {
        console.log(e);
      })
    },
  },
  mounted() {
    this.$router.replace({
      path: '/blog_sea/article_view/comment',
      query: {
        article: this.article
      }
    })
  },
  beforeRouteEnter(to,from,next){
    next(vm => {
      vm.article=to.query.article;
      if (vm.article.tags!==null){
        vm.article.tags=vm.article.tags.split(',');
      }
      if (vm.article.categories!==null){
        vm.article.categories=vm.article.categories.split(',');
      }
      vm.loadData(vm.article.authorId);
    })
  }
}
</script>

<style scoped>

</style>
