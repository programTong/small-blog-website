<template>
  <div>
    <div>
      <span>标题</span><input type="text" v-model="article.title">
    </div>
    <div>
      <span>标签</span>
      <input type="text" v-model="article.tags">
    </div>
    <div>
      <span>分类</span>
      <input type="text" v-model="article.categories">
    </div>
    <div>
      <input type="text" id="text_input_owernwox" v-model="article.content">
    </div>
    <div>
      <input type="button" value="保存" @click="save">
    </div>
  </div>
</template>

<script>
import GlobalVar from "../js/GlobalVar";

export default {
  name: "Article_Publish",
  data(){
    return {
      article: {},
    }
  },
  methods: {
    save: function (){
      fetch('http://localhost:8080/user/article/modify',{
        method: 'post',
        mode: 'cors',
        headers: {
          "Authorization": GlobalVar.user_auth_data.role+" "+GlobalVar.user_auth_data.token,
          "Content-Type": "application/json;charset=UTF-8"
        },
        body: JSON.stringify({
          cid: this.article.cid,
          title: this.article.title,
          content: this.article.content,
          status: "publish",
          type: "post",
          allowComment: true,
          tags: this.article.tags,
          categories: this.article.categories
        })
      }).then(response => {
        return response.json()
      }).then(json => {
        if (json.flag === true){
          alert("修改成功");
          this.$router.replace({
            path: '/article_manage'
          })
        }else{
          alert("修改失败");
        }
      }).catch(e => {
        console.log(e);
      })
    },
    loadData: function (article){
      this.article=article;
    },
  },
  beforeRouteEnter(to,from,next){
    next(vm => {
      vm.loadData(to.query.article);
    })
  }
}
</script>

<style scoped>
#text_input_owernwox{
  width: 600px;
  height: 500px;
}
</style>
