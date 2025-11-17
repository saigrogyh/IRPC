<script setup>
import { ref , onMounted} from 'vue'

const URL = 'http://localhost:8080/api/employees'
const employees = ref([]);
const form = ref({
  id : null,
  firstName : '',
  lastName : ''
})
const isEditing = ref(false);
const showForm = ref(false); 
const listEmployee = async () => {
  try{
    const response = await fetch(URL)
    if(!response.ok) throw new Error("โหลดไม่สำเร็จ")
    employees.value = await response.json()
  }catch (error){
    console.error(error)
  }
}
const handleSubmit = async () => {
  let url = URL
  let method = "POST"
  if(isEditing.value){
    method = "PUT"
    url = `${URL}/${form.value.id}`
  }
  try{
    const response = await fetch(url, {
      method: method,
      headers: {"Content-Type": "application/json"} ,
      body: JSON.stringify(form.value)
    })
    if(!response.ok) throw new Error("สร้าง/อัปเดตไม่สำเร็จ")
    listEmployee()
    cancelEdit()
  }catch (error){
    console.error(error)
  }
}
const deleteEmp = async (id) => {
  if (!confirm('แน่ใจว่าจะลบ?')) return;
  try{
    const response = await fetch(`${URL}/${id}`,{
      method: "DELETE"
    })
    if(!response.ok) throw new Error("ลบไม่สำเร็จ")
    listEmployee()
  }catch(error){
    console.error(error)
  }
}
const showCreateForm = () => {
  form.value = { id: null, firstName: '', lastName: '' };
  isEditing.value = false;
  showForm.value = true;
};
const editEmployee = (employee) => {
  form.value = { ...employee };
  isEditing.value = true;
  showForm.value = true;
};
const cancelEdit = () => {
  form.value = { id: null, firstName: '', lastName: '' }; 
  isEditing.value = false;
  showForm.value = false;
};
onMounted(listEmployee)
</script>

<template>
  <h1>Employee</h1>
  
  <form @submit.prevent="handleSubmit" class="form-container" v-if="showForm">
    <h3>{{ isEditing ? 'Update Employee' : 'Create Employee' }}</h3>
    
    <div class="form-inputs">
      <input type="text" v-model="form.firstName" placeholder="FirstName" required />
      <input type="text" v-model="form.lastName" placeholder="LastName" required />
      <button type="submit" class="btn-create">
        {{ isEditing ? 'Update' : 'Create' }}
      </button>
    </div>
    <button @click.prevent="cancelEdit" type="button" class="btn-cancel">
      Cancel
    </button>
  </form>

  <table>
    <thead>
      <tr>
        <th class="col-icon">
          <button @click="showCreateForm" v-if="!showForm" class="btn-add">+</button>
        </th> 
        <th>EmployeeID</th>
        <th>FirstName</th>
        <th>LastName</th>
        <th class="col-icon"></th> </tr>
    </thead>
    <tbody>
      <tr v-for="employee in employees" :key="employee.id">
        <td>
          <button @click="editEmployee(employee)" class="btn-icon">✏️</button>
        </td>
        <td>{{ employee.id }}</td>
        <td>{{ employee.firstName }}</td>
        <td>{{ employee.lastName }}</td>
        <td>
          <button @click="deleteEmp(employee.id)" class="btn-icon">🗑️</button>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<style>
  body { font-family: sans-serif; }

  .btn-add {
    font-size: 1.5rem;
    font-weight: bold;
    color: green;
    background: #e0fbe0;
    border: 1px solid green;
    border-radius: 50%;
    width: 30px;
    height: 30px;
    cursor: pointer;
  }

  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
  }
  th, td {
    border-bottom: 1px solid #ddd;
    padding: 12px;
    text-align: left;
  }
  thead {
    background-color: #f9f9f9;
  }

</style>