<script setup lang="ts">
import {onMounted, ref, Ref, watchEffect} from "vue";
import {ProductInterface, ProductType} from "../../interface/interfaces";
import ApiService from "../../services/apiService";
import {productTypes} from "../../enums/enums.js";
import AdminProductListComponent from "../../components/AdminProductListComponent.vue";
import ModalBox from "../../components/ModalBox.vue";

// create a ref to hold the list of products of the type
const productsAllData: Ref<ProductInterface[]> = ref([])
const products: Ref<ProductInterface[]> = ref([])
const stocks: Ref<number[]> = ref([])
const deleteConfirmation: Ref<boolean> = ref(false)
const emittedProduct: Ref<ProductInterface> = ref({
  artNum: "",
  color: "",
  model: "",
  price: 0,
  size: undefined,
  type: undefined,
})


const type: Ref<ProductType> = ref(ProductType.ALL);

const loadProductList = async () => {

  productsAllData.value = await ApiService.get("/api/products") as ProductInterface[];

  products.value = productsAllData.value
}

onMounted(() => {
  loadProductList()

})

watchEffect(() => {
  if (type.value.toUpperCase() !== ProductType.ALL.toUpperCase()) {
    products.value = productsAllData.value.filter(product => product.type === type.value.toUpperCase()).sort((a, b) => a.artNum.localeCompare(b.artNum))
  } else {
    products.value = productsAllData.value.sort((a, b) => a.artNum.localeCompare(b.artNum))
  }

})

function test(product: ProductInterface, active: boolean) {
  emittedProduct.value = product
  deleteConfirmation.value = active
}

function deleteProduct(answer: boolean) {
  if (answer) {
    ApiService.delete("/api/admin/products/" + emittedProduct.value.artNum).finally(loadProductList)
    deleteConfirmation.value = false
  }
}


</script>

<template>
  <div class="">
    <ModalBox title="Do you want to delete this item?" @call-on-answer="deleteProduct"
              :active="deleteConfirmation"></ModalBox>
    <div class="flex justify-center m-6 gap-6 items-center ">Filter type:

      <select v-model="type">
        <option v-for="type in productTypes" :value="type.toUpperCase()">{{ type }}</option>
      </select>
    </div>
    <AdminProductListComponent :product-list="products" @delete-product="test"/>
  </div>
</template>


