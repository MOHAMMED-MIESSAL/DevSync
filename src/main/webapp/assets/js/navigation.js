document.getElementById('sidebar-toggle-button').addEventListener('click', function() {
    document.getElementById('sidebar').classList.toggle('hidden');
    document.getElementById('main').classList.toggle('ml-64');
})

document.getElementById('notification-button').addEventListener('click', function(){
    document.getElementById('notifications').classList.toggle('hidden');
})