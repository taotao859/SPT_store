import Vuex from 'vuex'
import Vue from 'vue'

Vue.use(Vuex);

export default new Vuex.Store({
  state:{
    staff_id: "",
    staff_name:"",
    init_order_id: '',
    product_id : '',
    opStaff_id: '',
    customer_id:'',
    product_name:'',
  },
  mutations:{
    //保存用户id
    saveStaff_id(state, staff_id) {
      state.staff_id = staff_id;
    },
    saveStaff_name(state, staff_name) {
      state.staff_name = staff_name;
    },
    //保存零售初始化的id订单
    saveStaff_init_order_id(state, init_order_id) {
      state.init_order_id = init_order_id;
    },
    //保存商品Id
    saveProduct_id(state, product_id){
      state.product_id = product_id;
    },
    saveOpStaff_id(state,opStaff_id){
      state.opStaff_id = opStaff_id
    },
    saveCustomer_id(state,customer_id){
      state.customer_id = customer_id
    },
    saveProduct_name(state,product_name){
      state.product_name = product_name
    }
  }
})
