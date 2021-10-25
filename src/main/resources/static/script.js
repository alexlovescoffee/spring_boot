let metaData = {
    editBtn: '<button class="btn btn-info edit-btn">Edit</button>',
    deleteBtn: '<button class="btn btn-danger delete-btn">Delete</button>'
}

$(document).ready(function() {
    let $table = $('#main_table');

    $(document).on('click', '#submit_add_btn', function (e) {
        addUser();
    });

    $(document).on('click', '#submit_update_btn', function (e) {
        updateUser();
    });

    $(document).on('click', '#submit_delete_btn', function (e) {
        deleteUser();
    });

    $table.on('click', '.edit-btn', function (e) {
        //let $row = $table.bootstrapTable('getRowByUniqueId', $(this.parentNode.parentNode).data('uniqueid'));

        $.ajax({
            url: "boot/get-user?id=" + $(this.parentNode.parentNode).data('uniqueid'),
            type: "GET",
            success: function (data) {
                feelForm(data, "#edit_");
                $('#editUserModal').modal('show');
            },
            error: function (xhr) {
                iziToast.error({message: xhr.responseText});
            }
        });
    });

    $table.on('click', '.delete-btn', function (e) {
        $.ajax({
            url: "boot/get-user?id=" + $(this.parentNode.parentNode).data('uniqueid'),
            type: "GET",
            success: function (data) {
                feelForm(data, "#delete_");
                $('#deleteUserModal').modal('show');
            },
            error: function (xhr) {
                iziToast.error({message: xhr.responseText});
            }
        });
    });

});

function addUser() {
    let form = $('#add_form');

    if (checkIfValueIsEmpty(form.serializeArray(), 'roles[]'))
        return;

    $.ajax({
        type: "POST",
        url: "boot",
        data: JSON.stringify(form.serializeObject()),
        contentType: "application/json",
        cache: false,
        success: function (data, statusText, xhr) {
            console.log(data);

            let rolesBlock = '';
            for (let role of data.roles)
                rolesBlock += '<span class="role-span">' + role +'</span>'

            $('#main_table').bootstrapTable('append', {
                id: data.id,
                firstName: data.firstName,
                lastName: data.lastName,
                age: data.age,
                email: data.email,
                role: rolesBlock,
                edit: metaData.editBtn,
                delete: metaData.deleteBtn
            });

            $('#nav-users_table-tab').click();
            feelForm({roles: []}, '#add_');
            iziToast.success({message: 'added'});
        },
        error: function (xhr) {
            iziToast.error({message: xhr.responseText});
        }
    });
}

/*
$table.bootstrapTable('insertRow', {
        index: 1,
        row: {
          id: randomId,
          name: 'Item ' + randomId,
          price: '$' + randomId
        }
      })
* */

function updateUser() {
    let form = $('#edit_form');

    if (checkIfValueIsEmpty(form.serializeArray(), 'roles[]'))
        return;

    $.ajax({
        type: "PUT",
        url: "boot",
        data: JSON.stringify(form.serializeObject()),
        contentType: "application/json",
        cache: false,
        success: function (data, statusText, xhr) {
            console.log(data);
            iziToast.success({message: 'success'});
        },
        error: function (xhr) {
            iziToast.error({message: xhr.responseText});
        }
    });

}

function deleteUser() {
    let formSerialized = [{name: 'id', value: +$('#delete_id').val()}];

    if (checkIfValueIsEmpty(formSerialized))
        return;

    $.ajax({
        type: "DELETE",
        url: "boot?id=" + formSerialized[0].value,
        cache: false,
        success: function (data, statusText, xhr) {
            $('#close_deleteUserModal').click();
            iziToast.success({message: 'success'});
        },
        error: function (xhr) {
            iziToast.error({message: xhr.responseText});
        }
    });
}

function feelForm(data, prefix) {
    $(prefix + 'firstName').val(data.firstName);
    $(prefix + 'lastName').val(data.lastName);
    $(prefix + 'age').val(data.age);
    $(prefix + 'email').val(data.email);

    if (prefix === '#edit_' || prefix === '#delete_')
        $(prefix + 'id').val(data.id);

    if (prefix === '#edit_' || prefix === '#add_')
        $(prefix + 'password').val('');//data.password

    $(prefix + 'role_admin').prop("checked", data.roles.includes("ADMIN"));
    $(prefix + 'role_user').prop("checked", data.roles.includes("USER"));
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
        iziToast.warning({message: 'Field :' + emptyState.name + ': cannot be empty!'});
        return true;
    }
}