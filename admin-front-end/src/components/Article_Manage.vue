<template>
  <div>
    <table>
      <thead>
        <tr>
          <th>标题</th>
          <th>内容</th>
          <th>标签</th>
          <th>分类</th>
          <th>删除</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(article,i) in list">
          <td>{{article.title}}</td>
          <td>{{article.content.substr(0,article.content.length>20?20:article.content.length)}}</td>
          <td>{{article.tags}}</td>
          <td>{{article.categories}}</td>
          <td><input type="button" value="删除" @click="deleteArticle(i)"></td>
        </tr>
      </tbody>
    </table>
    <div>
      <input type="button" value="上一页" @click="getArticlePage(1)">
      <span>{{pageNum+'/'+pages}}</span>
      <input type="button" value="下一页" @click="getArticlePage(2)">
    </div>
  </div>
</template>

<script>
import GlobalVar from "../js/GlobalVar";

export default {
  name: "Article_Manage",
  data(){
    return {
      req_page: 1,
      req_page_size: 1,
      total: 3,
      pageNum: 1,
      pageSize: 2,
      size: 2,
      pages: 2,
      prePage: 0,
      nextPage: 2,
      isFirstPage: {},
      isLastPage: {},
      hasPreviousPage: {},
      hasNextPage: {},
      navigatePages: 8,
      firstPage: 1,
      lastPage: 2,
      list: {}
    }
  },
  methods: {
    deleteArticle: function (i){
      fetch('http://localhost:8080/admin/article/delete',{
        method: 'post',
        mode: 'cors',
        headers: {
          "Authorization": GlobalVar.user_auth_data.role+" "+GlobalVar.user_auth_data.token,
          "Content-Type": "application/json;charset=UTF-8"
        },
        body: JSON.stringify({
          cid: this.list[i].cid
        })
      }).then(response => {
        return response.json()
      }).then(json => {
        if (json.flag===true){
          alert("删除成功");
          this.loadData(this.req_page,this.req_page_size);
        }else{
          alert("删除失败")
        }
      }).catch(e => {
        console.log(e);
      })
    },
    getArticlePage: function (i){
      if (i===1){
        if (this.isFirstPage!==true){
         this.loadData(this.pageNum-1,this.req_page_size);
        }
      }else if (i===2){
        if (this.isLastPage!==true){
          this.loadData(this.pageNum+1,this.req_page_size);
        }
      }
    },
    loadData: function (req_page,req_page_size){
      fetch('http://localhost:8080/admin/article?page='+req_page+'&page_size='+req_page_size,{
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
          let data = json.data;
          this.total= data.total;
          this.pageNum= data.pageNum;
          this.pageSize= data.pageSize;
          this.size= data.size;
          this.pages= data.pages;
          this.prePage= data.prePage;
          this.nextPage= data.nextPage;
          this.isFirstPage= data.isFirstPage;
          this.isLastPage= data.isLastPage;
          this.hasPreviousPage= data.hasPreviousPage;
          this.hasNextPage= data.hasNextPage;
          this.navigatePages= data.navigatePages;
          this.firstPage= data.firstPage;
          this.lastPage= data.lastPage;
          this.list=data.list
        }else{
          alert("文章加载失败");
        }
      }).catch(e => {
        console.log(e);
      })
    }
  },
  beforeRouteEnter(to,from,next){
    next(vm => {
      vm.loadData(vm.req_page,vm.req_page_size);
    })
  }
}
</script>

<style scoped>

</style>
