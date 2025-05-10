document.addEventListener('DOMContentLoaded', function () {

    const baseUrl = document.body.dataset.baseUrl; // например, "/api/" или "/"

    document.querySelectorAll('.favorite-remove-btn').forEach(button => {
        button.addEventListener('click', function () {
            const id = this.dataset.id;

            fetch(`${baseUrl}/my/ingredients/delete/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.ok) {
                    // Обновить UI без перезагрузки
                    this.textContent = '🤍';
                    this.classList.remove('favorite-remove-btn');
                    this.classList.add('favorite-add-btn');
                } else {
                    alert('Ошибка удаления из избранного');
                }
            }).catch(err => {
                console.error(err);
                alert('Ошибка сети');
            });
        });
    });

    document.querySelectorAll('.favorite-add-btn').forEach(button => {
        button.addEventListener('click', function () {
            const id = this.dataset.id;

            fetch(`${baseUrl}/my/ingredients/add/${id}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.ok) {
                    this.textContent = '❤️';
                    this.classList.remove('favorite-add-btn');
                    this.classList.add('favorite-remove-btn');
                } else {
                    alert('Ошибка добавления в избранное');
                }
            }).catch(err => {
                console.error(err);
                alert('Ошибка сети');
            });
        });
    });
});
