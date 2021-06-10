<template>
  <div id="div_owrwpojvuwjerw">
    <div>发表评论</div>
    <div>
      <input type="text" v-model="content">
    </div>
    <div><input type="button" value="发表" @click="submit"></div>
    <div>评论</div>
    <div v-for="comment in comments">
      <input type="text" readonly v-model="comment.content">
    </div>
  </div>
</template>

<script>
import GlobalVar from "../js/GlobalVar";

export default {
  name: "Comment",

  data(){
    return {
      content: "",
      comments: {},
      article: {}
    }
  },
  methods: {
    loadData: function (){
      fetch('http://localhost:8080/article/comment?cid='+this.article.cid,{
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
          this.comments=json.data;
        }else{
          alert("评论加载失败");
        }
      }).catch(e => {
        console.log(e);
      })
    },
    submit: function (){
      fetch('http://localhost:8080/article/comment',{
        method: 'post',
        mode: 'cors',
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
          "Authorization": GlobalVar.user_auth_data.role+" "+GlobalVar.user_auth_data.token
        },
        body: JSON.stringify({
          cid: this.article.cid,
          authorId: GlobalVar.user.uid,
          ownerId: this.article.authorId,
          content: this.content,
        })
      }).then(response => {
        return response.json()
      }).then(json => {
        console.log(json);
        if (json.flag === true){
          this.user = json.data;
          alert("发表评论成功");
          this.loadData();
        }else{
          alert("发表评论失败");
        }
      }).catch(e => {
        console.log(e);
      })
    },
  },

  beforeRouteEnter(to,from,next){
    next(vm => {
      vm.article=to.query.article;
      vm.loadData();
    })
  }
}
</script>

<style scoped>
#div_owrwpojvuwjerw >*{
  margin: 10px 0px;
}
</style>
