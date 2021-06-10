<template>
  <div>
    <table>
      <thead>
      <tr>
        <th>作者编号</th>
        <th>内容</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="comment in list">
        <td>{{comment.authorId}}</td>
        <td>{{comment.content}}</td>
        <td><input type="button" value="删除" @click="deleteComment(comment)"></td>
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
  name: "Comment_Manage",
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
    deleteComment: function (comment){
      fetch('http://localhost:8080/admin/comments/delete',{
        method: 'post',
        mode: 'cors',
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
          "Authorization": GlobalVar.user_auth_data.role+" "+GlobalVar.user_auth_data.token
        },
        body: JSON.stringify({
          coid: comment.coid
        })
      }).then(response => {
        return response.json()
      }).then(json => {
        if (json.flag === true){
          alert("删除分类成功");
          this.loadData(this.req_page,this.req_page_size);
        }else{
          alert("删除分类失败");
        }
      }).catch(e => {
        console.log(e);
      })
    },
    loadData: function (req_page,req_page_size){
      fetch('http://localhost:8080/admin/comments?page='+req_page+'&page_size='+req_page_size,{
        method: 'get',
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
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
          alert("评论加载失败");
        }
      }).catch(e => {
        console.log(e);
      })
    },
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
