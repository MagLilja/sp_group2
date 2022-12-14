import {createRouter, createWebHistory} from "vue-router";
import home from "../views/home.vue";
import admin from "../views/admin.vue";
import LoginView from "../views/auth/LoginView.vue";
import {useAuthStore} from "../stores/authStore";
const router = createRouter({
    history: createWebHistory('/'),
    routes: [
        {
            path: '/',
            name: 'home',
            component: home,
        },
        {
            path: '/shop',
            name: 'shop',
            component: () => import('../views/shop.vue'),
        },
        {
            path: '/about',
            name: 'about',
            component: () => import('../views/about.vue'),
        },
        {
            path: '/login',
            name: 'login',
            component: LoginView,
        },
        {
            path: '/categories',
            name: 'categories',
            component: () => import('../views/categories.vue'),
        },
        {
            path: '/product/:artNr',
            name: 'productPage',
            component: () => import('../views/productView.vue'),
            props: true,
        },
        {
            path: '/admin',
            name: 'admin',
            component: admin,
            children: [
                {
                    path: 'add-product',
                    name: 'add-product',
                    component: () => import('../views/admin/add-product.vue'),
                },
                {
                    path: 'show-products',
                    name: 'show-products',
                    component: () => import('../views/admin/show-products.vue'),
                },
            ]

        }
    ]
})


router.beforeEach(async (to) => {
    const authRequired = to.path.includes("admin")
    const auth = useAuthStore();

    if (authRequired && !auth.user) {
        auth.returnUrl = to.fullPath;
        return '/login';
    }
});

export default router