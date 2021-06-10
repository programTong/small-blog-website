import VueRouter from 'vue-router'
import Vue from 'vue'
import Status from '../components/Status.vue'
import Article_Manage from '../components/Article_Manage.vue'
import Category_Manage from '../components/Category_Manage.vue'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import UserView from '../components/UserView.vue'
import App_User from '../components/App_User.vue'
import Comment_Manage from '../components/Comment_Manage.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '',
        components: {
            body_router_view: Login,
        }
    },
    {
        path: '/user_view',
        components: {
            body_router_view: UserView
        }
    },
    {
        path: '/comment_manage',
        components: {
            body_router_view: Comment_Manage
        }
    },
    {
        path: '/login',
        components: {
            body_router_view: Login
        }
    },
    {
        path: '/register',
        components: {
            body_router_view: Register
        }
    },
    {
        path: '/status',
        components: {
            body_router_view: Status,
        }
    },

    {
        path: '/article_manage',
        components: {
            body_router_view: Article_Manage
        }
    },
    {
        path: '/category_manage',
        components: {
            body_router_view: Category_Manage
        }
    },
]

for (let i = 0; i < routes.length; i++) {
    let route = routes[i];
    route.components.app_user=App_User;
}

const router = new VueRouter({
    routes,
    mode: 'history'
})

export default router
