document.addEventListener("DOMContentLoaded", function () {
    let currentPage = 1;
    const pageSize = 1;
    const searchFields = ["memberId", "email", "name", "startDate", "endDate"];

    function setInitialSearchParams() {
        const urlParams = new URLSearchParams(window.location.search);
        searchFields.forEach(function (field) {
            const input = document.getElementById(field);
            if (input) {
                input.value = urlParams.get(field) || "";
            }
        });
    }

    function getSearchParams() {
        const params = {};
        searchFields.forEach(function (field) {
            const input = document.getElementById(field);
            if (input && input.value) {
                params[field] = input.value;
            }
        });
        return params;
    }

    function loadMembers(page) {
        const params = getSearchParams();
        params.page = page;
        params.size = pageSize;

        const queryParams = new URLSearchParams(params).toString();
        window.history.replaceState(null, '', `/members?${queryParams}`);

        fetch(`/api/members?${queryParams}`)
            .then(window.handleErrorResponse)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                renderMembers(data.members);
                updatePagination(data.totalPages, page);
            });
    }

    function renderMembers(members) {
        const tableBody = document.getElementById("memberTableBody");
        tableBody.innerHTML = "";
        members.forEach(function (member) {
            const row = `
                <tr>
                    <td>${member.memberId}</td>
                    <td>${member.email}</td>
                    <td>${member.name}</td>
                    <td>${member.joinDate}</td>
                </tr>`;
            tableBody.insertAdjacentHTML("beforeend", row);
        });
    }

    function updatePagination(totalPages, currentPage) {
        const pagination = document.getElementById("pagination");
        pagination.innerHTML = "";
        for (let i = 1; i <= totalPages; i++) {
            const pageButton = document.createElement("button");
            pageButton.textContent = i;
            pageButton.disabled = i === currentPage;
            pageButton.addEventListener("click", function () {
                loadMembers(i);
            });
            pagination.appendChild(pageButton);
        }
    }

    document.getElementById("searchBtn").addEventListener("click", function () {
        currentPage = 1;
        loadMembers(currentPage);
    });

    document.getElementById("clearBtn").addEventListener("click", function () {
        searchFields.forEach(function (field) {
            const input = document.getElementById(field);
            if (input) {
                input.value = "";
            }
        });
        window.history.replaceState(null, '', '/members');
    });

    document.getElementById("downloadBtn").addEventListener("click", function () {
        const params = getSearchParams();
        const queryParams = new URLSearchParams(params).toString();
        window.location.href = `/api/members/export?${queryParams}`;
    });

    document.getElementById('openModalBtn').addEventListener('click', function () {
        document.getElementById('memberAddModal').style.display = 'block';
    });

    document.getElementById('closeModalBtn').addEventListener('click', function () {
        document.getElementById('memberAddModal').style.display = 'none';
    });

    setInitialSearchParams();
    loadMembers(currentPage);
});
