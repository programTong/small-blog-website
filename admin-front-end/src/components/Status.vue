<template>
  <div>
    <div>文章数: {{article_count}}</div>
    <div>评论数: {{comment_count}}</div>
  </div>
</template>

<script>
import GlobalVar from "../js/GlobalVar";
export default {
  name: "Status",
  data(){
    return {
      article_count: 0,
      comment_count: 0,
    }
  },
  methods: {
    loadData: function (){
      fetch('http://localhost:8080/admin/index',{
        method: 'get',
        mode: 'cors',
        headers: {
          "Authorization": GlobalVar.user_auth_data.role+" "+GlobalVar.user_auth_data.token
        },
      }).then(response => {
        return response.json()
      }).then(json => {
        if (json.flag === true){
          console.log(json);
          this.article_count=json.data.statistics.articles;
          if (json.data.statistics.comments!==null){
            this.comment_count=json.data.statistics.comments;
          }
        }else{
          alert("文章数量,评论数量夹在失败");
        }
      }).catch(e => {
        console.log(e);
      })
    }
  },
  beforeRouteEnter(to,from,next){
    next(vm => {
      vm.loadData();
    })
  }
}
</script>

<style scoped>

</style>
