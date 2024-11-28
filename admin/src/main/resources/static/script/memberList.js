document.addEventListener("DOMContentLoaded", function () {
    let currentPage = 1;
    const pageSize = 1;

    const searchFields = {
        "filter-memberId": "memberId",
        "filter-email": "email",
        "filter-name": "name",
        "filter-startDate": "startDate",
        "filter-endDate": "endDate"
    };

    function setInitialSearchParams() {
        const urlParams = new URLSearchParams(window.location.search);
        Object.keys(searchFields).forEach(function (filterId) {
            const input = document.getElementById(filterId);
            if (input) {
                input.value = urlParams.get(searchFields[filterId]) || "";
            }
        });
    }

    function getSearchParams() {
        const params = {};
        Object.entries(searchFields).forEach(function ([filterId, fieldName]) {
            const input = document.getElementById(filterId);
            if (input && input.value) {
                params[fieldName] = input.value;
            }
        });
        return params;
    }

    function loadMembers(page) {
        if (page < 1) page = 1;

        const params = getSearchParams();
        params.page = page - 1;
        params.size = pageSize;

        const queryParams = new URLSearchParams(params).toString();
        window.history.replaceState(null, '', `/members?${queryParams}`);

        fetch(`/api/members?${queryParams}`)
            .then(window.handleErrorResponse)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                const members = data.result.content;
                const totalPages = data.result.totalPages;

                renderMembers(members);
                updatePagination(totalPages, page);
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
        for (let i = 1; i <= totalPages; i++) { // 화면에 1부터 시작하는 번호 표시
            const pageButton = document.createElement("button");
            pageButton.textContent = i; // 1부터 표시
            pageButton.disabled = i === currentPage;
            pageButton.addEventListener("click", function () {
                loadMembers(i); // 화면 번호를 그대로 사용
            });
            pagination.appendChild(pageButton);
        }
    }

    document.getElementById("searchBtn").addEventListener("click", function () {
        currentPage = 1;
        loadMembers(currentPage);
    });

    document.getElementById("clearBtn").addEventListener("click", function () {
        Object.keys(searchFields).forEach(function (filterId) {
            const input = document.getElementById(filterId);
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

    // 회원 추가 요청 처리
    document.getElementById('addMemberBtn').addEventListener('click', function () {
        const name = document.getElementById('register-name').value;
        const email = document.getElementById('register-email').value;
        const role = document.getElementById('register-role').value;

        if (!name || !email || !role) {
            alert("모든 필드를 입력해주세요.");
            return;
        }

        const memberData = {
            name,
            email,
            role
        };

        fetch('/api/members', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(memberData)
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => {
                        throw new Error(err.message || "회원 추가 실패");
                    });
                }
                return response.json();
            })
            .then(data => {
                alert("회원이 성공적으로 추가되었습니다.");
                document.getElementById('memberAddModal').style.display = 'none';
                loadMembers(currentPage);
            })
            .catch(error => {
                alert(`오류 발생: ${error.message}`);
            });
    });

    setInitialSearchParams();
    loadMembers(currentPage);
});
