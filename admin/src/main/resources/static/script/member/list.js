document.addEventListener("DOMContentLoaded", () => {
    let currentPage = 1;
    const pageSize = 1;

    const searchFields = {
        "filter-memberId": "memberId",
        "filter-email": "email",
        "filter-name": "name",
        "filter-status": "status",
        "filter-startDate": "startDate",
        "filter-endDate": "endDate"
    };

    const setInitialSearchParams = () => {
        const urlParams = new URLSearchParams(window.location.search);
        Object.keys(searchFields).forEach(filterId => {
            const input = document.getElementById(filterId);
            if (input) {
                input.value = urlParams.get(searchFields[filterId]) || "";
            }
        });
    };

    const getSearchParams = () => {
        const params = {};
        Object.entries(searchFields).forEach(([filterId, fieldName]) => {
            const input = document.getElementById(filterId);
            if (input && input.value) {
                params[fieldName] = input.value;
            }
        });
        return params;
    };

    const loadMembers = (page = 1) => {
        if (page < 1) page = 1;

        const params = getSearchParams();
        params.page = page - 1;
        params.size = pageSize;

        const queryParams = new URLSearchParams(params).toString();
        window.history.replaceState(null, '', `/members?${queryParams}`);

        fetch(`/api/members?${queryParams}`)
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        const errorMessage = errorData.message || "회원 추가 실패";
                        throw new Error(errorMessage);
                    });
                }
                return response.json();
            })
            .then(data => {
                const members = data.result.content;
                const totalPages = data.result.totalPages;

                renderMembers(members);
                updatePagination(totalPages, page);
            })
            .catch(error => {
                alert(`${error.message}`);
            });
    };

    const renderMembers = members => {
        const tableBody = document.getElementById("memberTableBody");
        tableBody.innerHTML = "";

        members.forEach(member => {
            const row = `
                <tr>
                    <td>${member.memberId}</td>
                    <td>${member.email}</td>
                    <td>${member.name}</td>
                    <td>${member.role}</td>
                    <td>${member.status}</td>
                    <td>${member.joinDate}</td>
                    <td>
                        <button class="editMemberBtn" data-id="${member.memberId}">
                            Edit
                        </button>
                    </td>
                </tr>`;
            tableBody.insertAdjacentHTML("beforeend", row);
        });
    };

    const updatePagination = (totalPages, currentPage) => {
        const pagination = document.getElementById("pagination");
        pagination.innerHTML = "";

        for (let i = 1; i <= totalPages; i++) {
            const pageButton = document.createElement("button");
            pageButton.textContent = i;
            pageButton.disabled = i === currentPage;
            pageButton.addEventListener("click", () => loadMembers(i));
            pagination.appendChild(pageButton);
        }
    };

    document.getElementById("searchBtn").addEventListener("click", () => {
        currentPage = 1;
        loadMembers(currentPage);
    });

    document.getElementById("clearBtn").addEventListener("click", () => {
        Object.keys(searchFields).forEach(filterId => {
            const input = document.getElementById(filterId);
            if (input) {
                input.value = "";
            }
        });
        window.history.replaceState(null, '', '/members');

        // 목록 갱신
        window.location.reload();
    });

    // 초기 설정
    setInitialSearchParams();
    loadMembers(currentPage);
});
