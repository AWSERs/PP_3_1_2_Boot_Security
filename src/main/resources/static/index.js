let userIn = document.querySelector('#roleUserIn');
let adminBtn = document.querySelector('#adminTab');
let userBtn = document.querySelector('#userTab')
let panelTitle = document.querySelector('#panel__title')
let userTableBtn = document.querySelector('#userTableBtn')
let newUserTableBtn = document.querySelector('#newUserTableBtn')
let userPanel = document.querySelector('#userPanel')
let allUsers = document.querySelector('#allUsers')
let newUser = document.querySelector('#newUser')

if (userIn.innerHTML === "ROLE_USER"){
    adminBtn.style.display='none'
    userTableBtn.style.display='none'
    userBtn.classList.add('active')
    newUserTableBtn.style.display='none'
    userPanel.classList.add('show')
    panelTitle.innerHTML = "User information-page"
} else if (userIn.innerHTML === "ROLE_ADMIN"){
    adminBtn.style.display='normal'
    userTableBtn.style.display='normal'
    newUserTableBtn.style.display='normal'
    panelTitle.innerHTML = "Admin Panel"
}

userBtn.onclick = function() {
    if(userIn.innerHTML === "ROLE_ADMIN")
    {
        panelTitle.innerHTML = "User information-page"
        userPanel.classList.add('show')
        userTableBtn.style.display='none'
        newUserTableBtn.style.display='none'
        allUsers.classList.remove('show')
        newUser.classList.remove('show')

    }
}

adminBtn.onclick = function (){
    if(userIn.innerHTML === "ROLE_ADMIN"){
        panelTitle.innerHTML = "Admin Panel"
        userTableBtn.style.display='inline-block'
        newUserTableBtn.style.display='inline-block'
        userPanel.classList.remove('show')
    }
}
