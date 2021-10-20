$(document).ready(function() {
    $('#add_form').submit(function (e) {
        e.preventDefault();

        let form = $('#add_form');
        if (checkIfValueIsEmpty(form.serializeArray(), 'roles[][role]'))
            return;

        $.ajax({
            type: "POST",
            url: 'admin/add',
            data: JSON.stringify(form.serializeObject()),
            contentType : 'application/json',
            dataType: 'json',
            cache: false,
            success: function (data) {
                reloadUsersTable(data);
            },
            error: function (xhr) {
                alert(xhr.responseText);
            }
        });
    });

    $('#update_form').submit(function (e) {
        e.preventDefault();

        let form = $('#update_form');
        if (checkIfValueIsEmpty(form.serializeArray(), 'roles[][role]'))
            return;

        $.ajax({
            type: "POST",
            url: 'admin/update',
            data: JSON.stringify(form.serializeObject()),
            contentType : 'application/json',
            //ф-ция $.ajax() узнает о типе присланных сервером данных от самого сервера (средствами MIME). Но также
            //указать это значение явно: xml, html, script, json, jsonp, text
            //dataType: 'json',
            cache: false,
            success: function (data, statusText, xhr) {
                if (xhr.status === 205)
                    window.location.pathname = xhr.getResponseHeader('re-authenticate');
                else
                    reloadUsersTable(data);
            },
            error: function (xhr) {
                alert(xhr.responseText);
            }
        });
    });

    $('#delete_form').submit(function (e) {
        e.preventDefault();

        let form = $('#delete_form');
        if (checkIfValueIsEmpty(form.serializeArray()))
            return;

        $.post("/admin/delete?" + form.serialize())
            .done((data, statusText, xhr) => {
                if (xhr.status === 205)
                    window.location.pathname = xhr.getResponseHeader('re-authenticate');
                else
                    reloadUsersTable(data);
            }).error((xhr) => {
                alert(xhr.responseText);
            });
    });
});

function reloadUsersTable(data) {
    let innerBody = '';
    data.forEach(function (user) {
        let rolesBlock = '';
        for (role of user.roles)
            rolesBlock += '<span class="role-span">' + role +'</span>'
        innerBody +=
            '<tr>' +
                '<td>' + user.id + '</td>' +
                '<td>' + user.name + '</td>' +
                '<td>' + user.password + '</td>' +
                '<td>' + rolesBlock + '</td>' +
            '</tr>'
    });
    document.querySelector('.users-table tbody').innerHTML = innerBody;
}

function checkIfValueIsEmpty(form, checkboxName) {
    //serializeArray() не захватывает пустые чекбоксы, поэтому имя чекбокса передается отдельно. Если оно присутствует в
    //массиве, значит по крайней мере один из чекбоксов не пустой, если имени нет - оба чекбокса пусты
    //если одно из полей пустое то возвращаем true
    let emptyState = {name: checkboxName};
    for (let field of form) {
        if (field.value === '') {
            emptyState.name = field.name;
            break;
        }
        if (checkboxName && emptyState.name && field.name === checkboxName)
            emptyState.name = undefined;
    }
    if (emptyState.name) {
        alert('Field <' + emptyState.name + '> cannot be empty!');
        return true;
    }
}