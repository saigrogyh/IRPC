<script setup>
  import { Update } from 'vite';
import { ref , onMounted} from 'vue'
  const URL = 'http://localhost:8080/irpc'

  const resEmp = ref([]);
  const sendReqEmp = ref({
    id : null,
    firstName : '',
    lastname : ''
  })

  const listEmployee = async () => {
    try{
    const response = await fetch(URL)
    if(!response.ok){
      throw new Error("โหลดไม่สำเร็จ")
    }
    resEmp.value = await response.json()
    }catch (error){
      console.error(error)
    }
  }

  const isUpdate = ref(false)

  const createAndUpdateEmp = async () => {
    let url = URL
    let method = "POST"
    if(isUpdate.value){
      method = "PUT"
      url = `${URL}/${sendReqEmp.value.id}`

    }

    try{
      const response = await fetch(url, {
        method: method,
        headers: {"Content-Type": "application/json"} ,
        body: JSON.stringify(sendReqEmp.value)
      })
      if(!response.ok){
        throw new Error("สร้างไม่สำเร็จ")
      }
      listEmployee()
    }catch (error){
      console.error(error)
    }
  }

  // const updateEmp = async (id) => {

  // }

  const deleteEmp = async (id) => {
    try{
      const response = await fetch(`${URL}/${id}`,{
        method: "DELETE"
      })
      if(!response.status(204)){
        throw new Error("ลบไม่สำเร็จ")
      }
      listEmployee()
    }catch(error){
      console.error(error)
    }
  }
</script>

<template>
  <form @submit.prevent="createAndUpdateEmp" class="formcreate">
    
  </form>

  <table>
    <thead>
      <tr>
        <th></th>
        <th>EmployeeID</th>
        <th>FirstName</th>
        <th>LastName</th>
        <th>Delete</th>
      </tr>
    </thead>
    <tbody>

    </tbody>
  </table>
</template>

<style scoped>

</style>
