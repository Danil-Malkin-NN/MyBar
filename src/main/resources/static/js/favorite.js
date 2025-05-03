document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.favorite-btn').forEach(button => {
        button.addEventListener('click', function () {
            const ingredientId = this.dataset.id;
            const basePath = window.appContextPath || '';

            fetch(origin + '/my/ingredients/add/' + ingredientId, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.ok) {
                    this.textContent = '❤️';
                    this.classList.remove('text-gray-500');
                    this.classList.add('text-red-500');
                    this.disabled = true;
                } else {
                    alert('Ошибка при добавлении в избранное');
                }
            }).catch(err => {
                console.error(err);
                alert('Ошибка сети');
            });
        });
    });
});
