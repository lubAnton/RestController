"use strict";

const urlUsers = "http://localhost:8080/rest";
const urlPrincipal = "rest/api/auth";


//заполняем данные авторизованного пользователя
async function getPrincipal() {
    let user  = await fetch(urlPrincipal);
    let userObj = await user.json();
    navbarDetail(userObj);
}
function navbarDetail (principal) {
    let userDetail = document.getElementById("principalInfo");
    let roles = "";
    for (let role of principal.roles) {
        roles+=role.name+" ";
    }
    userDetail.insertAdjacentHTML('beforeend', `
    <b>${principal.username}</b> with role: <a>${roles}</a>`)
}

window.addEventListener("DOMContentLoaded", getPrincipal);

// Запоняем данные пользователей

async function getAdminPage() {
    let users = await fetch(urlUsers);
    let allUsers = await users.json();
    loadMainTable(allUsers);
}
function loadMainTable(allUsers) {
    let tbody = document.getElementById("tbody");
    let data = "";
    for (let user of allUsers) {
        let roles = [];
        for (let role of user.roles) {
            roles.push(" " + role.name);
        }
        data +=
            `<tr>
    <td>${user.id}</td>
    <td>${user.name}</td>
    <td>${user.surname}</td>
    <td>${user.age}</td>
    <td>${user.username}</td>
    <td>${roles}</td>
    <td>
        <button class="btn btn-info" data-bs-toogle="modal"
        data-bs-target="#modalEdit"
        onclick="editUserInfo(${user.id})">Edit</button>
    </td>
        <td>
        <button class="btn btn-danger" data-bs-toogle="modal"
        data-bs-target="#deleteModal"
        onclick="deleteUserInfo(${user.id})">Delete</button>
    </td>
</tr>`
    }
    tbody.innerHTML = data;
}
getAdminPage();
window.addEventListener("DOMContentLoaded", loadMainTable);

//Заполняем таьлицу пользователя

window.addEventListener('DOMContentLoaded', userTab);
async function userTab(){
    let tableUser = document.getElementById('tbodyUser');
    let user = await fetch(urlPrincipal);
    let userObj;
    if (user.ok) {
        userObj = await user.json();
    } else {
        alert(`Ошибка, ${user.status}`);
    }
    let dataHTML='';
    let roles = [];
    for(let role of userObj.roles){
        roles.push(" "+role.name);
    }
    dataHTML+=
        `<tr>
            <td>${userObj.id}</td>
            <td>${userObj.name}</td>
            <td>${userObj.surname}</td>
            <td>${userObj.age}</td>
            <td>${userObj.username}</td>
            <td>${roles}</td>
        </tr>`
    tableUser.innerHTML = dataHTML;
}

//добавляем нового пользователя

const formNew = document.getElementById('formNewUser');
function addNewUser(){
    formNew.addEventListener('submit', newUser);
}
async function newUser(event) {
    event.preventDefault();
    let roles=[];

    for(let i = 0; i<formNew.rolesNew.options.length; i++) {
        if(formNew.rolesNew.options[i].selected) {
            roles.push({
                id: formNew.rolesNew.options[i].value,
                role: formNew.rolesNew.options[i].text
            });
        }
    }
    let attribute = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: formNew.name.value,
            surname: formNew.lastName.value,
            age: formNew.age.value,
            username: formNew.username.value,
            password: formNew.password.value,
            roles: roles})
    }
    console.log(attribute);
    await fetch(urlUsers, attribute).then(()=>{
        formNew.reset();
        getAdminPage();
    })
}

//Модальное окно изменения пользователя
let formEdit = document.getElementById('modalEditForm');
let idEdit = document.getElementById('idUp');
let nameEdit = document.getElementById('nameUp');
let surnameEdit = document.getElementById('surnameUp');
let ageEdit = document.getElementById('ageUp');
let usernameEdit = document.getElementById('usernameUp');
let passwordEdit = document.getElementById('passwordUp');
async function editUserInfo(id) {
    $('#modalEdit').modal('show');
    let urlEdit = 'http://localhost:8080/rest/' + id;
    let userInfo = await fetch(urlEdit);
    await userInfo.json().then(user=>{
        idEdit.value = `${user.id}`;
        nameEdit.value = `${user.name}`;
        surnameEdit.value = `${user.surname}`;
        ageEdit.value = `${user.age}`;
        usernameEdit.value = `${user.username}`;
        passwordEdit.value = `${user.password}`;
    });
}


async function editUser() {
    let urlEdit = "http://localhost:8080/rest/"+idEdit.value;
    let rolesEdit=[];

    for(let i = 0; i<formEdit.rolesUp.options.length; i++) {
        if(formEdit.rolesUp.options[i].selected) {
            rolesEdit.push({
                id: formEdit.rolesUp.options[i].value,
                role: formEdit.rolesUp.options[i].text
            });
        }
    }
    let attributeEdit = {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: formEdit.name.value,
            surname: formEdit.surname.value,
            age: formEdit.age.value,
            username: formEdit.username.value,
            password: formEdit.password.value,
            roles: rolesEdit})
    }
    await fetch(urlEdit, attributeEdit).then(()=>{
        $('#closeModalEdit').click();
        getAdminPage();
    })
}
//модальное окно удаления пользователя
function deleteUserInfo(id){

}