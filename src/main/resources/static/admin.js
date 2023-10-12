"use strict";

const urlUsers = "http://localhost:8080/rest";
const urlPrincipal = "rest/api/auth";


//заполняем данные авторизованного пользователя
async function getPrincipal() {
    let user = await fetch(urlPrincipal);
    let userObj = await user.json();
    navbarDetail(userObj);
}

function navbarDetail(principal) {
    let userDetail = document.getElementById("principalInfo");
    let roles = "";
    for (let role of principal.roles) {
        roles += role.name.replace("ROLE_", "") + " ";
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

const pills = document.querySelectorAll('.pill');
const pillData = document.querySelectorAll('.pillContent');

async function activePill(attributePill) {
    pillData.forEach((clickedContent) => {
        clickedContent.classList.contains(attributePill) ?
            clickedContent.classList.add('active') :
            clickedContent.classList.remove('active');
    });
};

pills.forEach((clickPill) => {
    clickPill.addEventListener('click', async () => {
        pills.forEach((pill) => {
            pill.classList.remove('active');
        });
        clickPill.classList.add('active');
        let atributePill = clickPill.getAttribute('id');
        await activePill(atributePill);
    })
})


function loadMainTable(allUsers) {
    let tbody = document.getElementById("tbody");
    let data = "";
    for (let user of allUsers) {
        let roles = [];
        for (let role of user.roles) {
            roles.push(" " + role.name.replace("ROLE_", ""));
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
        data-bs-target="#modalDelete"
        onclick="deleteUserInfo(${user.id})">Delete</button>
    </td>
</tr>`
    }
    tbody.innerHTML = data;
}

// getAdminPage();
window.addEventListener("DOMContentLoaded", loadMainTable);
getAdminPage();
//Заполняем таблицу пользователя

window.addEventListener('DOMContentLoaded', userTab);

async function userTab() {
    let tableUser = document.getElementById('tbodyUser');
    let user = await fetch(urlPrincipal);
    let userObj;
    if (user.ok) {
        userObj = await user.json();
    } else {
        alert(`Ошибка, ${user.status}`);
    }
    let dataHTML = '';
    let roles = [];
    for (let role of userObj.roles) {
        roles.push(" " + role.name.replace("ROLE_", ""));
    }
    dataHTML +=
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

const tabs = document.querySelectorAll('.taba');
const tabsData = document.querySelectorAll('.tabaContent');

async function activeTab(idTab) {
    tabsData.forEach((clickedData) => {
        clickedData.classList.contains(idTab) ?
            clickedData.classList.add('active') :
            clickedData.classList.remove('active');
    });
}

tabs.forEach((clicked) => {
    clicked.addEventListener('click', async () => {
        tabs.forEach((table) => {
            table.classList.remove('active');
        });
        clicked.classList.add('active');
        let idTab = clicked.getAttribute('id');
        await activeTab(idTab);
    });
});


//добавляем нового пользователя

const formNew = document.getElementById('formNewUser');

async function addNewUser() {
    formNew.addEventListener('submit', newUser);
}

async function newUser(event) {
    event.preventDefault();
    let roles = [];

    for (let i = 0; i < formNew.rolesNew.options.length; i++) {
        if (formNew.rolesNew.options[i].selected) {
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
            roles: roles
        })
    }
    await fetch(urlUsers, attribute).then(() => {
        formNew.reset();
        getAdminPage();
        activeTab('home-tab');
        document.getElementById('home-tab').classList.add('active');
        document.getElementById('new-user-tab').classList.remove('active');
    });
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
    await userInfo.json().then(user => {
        idEdit.value = `${user.id}`;
        nameEdit.value = `${user.name}`;
        surnameEdit.value = `${user.surname}`;
        ageEdit.value = `${user.age}`;
        usernameEdit.value = `${user.username}`;
        passwordEdit.value = `${user.password}`;
    });
}


async function editUser() {
    let urlEdit = "http://localhost:8080/rest/" + idEdit.value;
    let rolesEdit = [];

    for (let i = 0; i < formEdit.rolesUp.options.length; i++) {
        if (formEdit.rolesUp.options[i].selected) {
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
            id: formEdit.editId.value,
            name: formEdit.name.value,
            surname: formEdit.surname.value,
            age: formEdit.age.value,
            username: formEdit.username.value,
            password: formEdit.password.value,
            roles: rolesEdit
        })
    }
    await fetch(urlEdit, attributeEdit).then(() => {
        $('#closeModalEdit').click();
        getAdminPage();
    })
}

//модальное окно удаления пользователя

let formDel = document.getElementById('formForDelete');
let idDel = document.getElementById('idDel');
let idName = document.getElementById('nameDel');
let idSurname = document.getElementById('surnameDel');
let idAge = document.getElementById('ageDel');
let idUsername = document.getElementById('usernameDel');
let idRoles = document.getElementById('rolesDel');

async function deleteUserInfo(id) {
    $('#modalDelete').modal('show');
    let urlDel = 'http://localhost:8080/rest/' + id;
    let userInfoDel = await fetch(urlDel);
    if (userInfoDel.ok) {
        await userInfoDel.json().then(userDel => {
            idDel.value = `${userDel.id}`;
            idName.value = `${userDel.name}`;
            idSurname.value = `${userDel.surname}`;
            idAge.value = `${userDel.age}`;
            idUsername.value = `${userDel.username}`;
            idRoles.value = `${userDel.roles}`;
        })
    } else {
        alert(`error, ${userInfoDel.status}`);
    }
}

async function deleteUser() {
    let urlDel = 'http://localhost:8080/rest/' + idDel.value;
    let attributeDel = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: formDel.idDelete.value,
            name: formDel.name.value,
            surname: formDel.surname.value,
            age: formDel.age.value,
            username: formDel.login.value,
            roles: formDel.rolesDelUser.value
        })
    }

    await fetch(urlDel, attributeDel).then(() => {
        $('#deleteCloseBtn').click();
        getAdminPage();
    });
}