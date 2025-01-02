document.addEventListener("DOMContentLoaded", () => {
    const memberEditModal = document.getElementById("memberEditModal");

    // 입력 필드 및 초기값 설정
    const inputs = {
        memberId: {
            element: document.getElementById("edit-memberId"),
            defaultValue: ""
        },
        name: {
            element: document.getElementById("edit-name"),
            defaultValue: ""
        },
        email: {
            element: document.getElementById("edit-email"),
            defaultValue: ""
        },
        role: {
            element: document.getElementById("edit-role"),
            defaultValue: "USER"
        },
        status: {
            element: document.getElementById("edit-status"),
            defaultValue: "ACTIVE"
        }
    };

    // 입력 값 초기화 함수
    const resetInputs = () => {
        Object.values(inputs).forEach(input => {
            input.element.value = input.defaultValue;
        });
    };

    // 입력 값 설정 함수
    const setInputs = data => {
        Object.entries(data).forEach(([key, value]) => {
            if (inputs[key]) {
                inputs[key].element.value = value;
            }
        });
    };

    // 입력 값 가져오기 함수
    const getInputValues = () => {
        const values = {};
        Object.entries(inputs).forEach(([key, input]) => {
            values[key] = input.element.value;
        });
        return values;
    };

    // Edit 버튼 클릭 시 회원 정보 가져오기
    document.getElementById("memberTableBody").addEventListener("click", event => {
        if (event.target.classList.contains("editMemberBtn")) {
            const memberId = event.target.getAttribute("data-id");

            // API 호출하여 회원 정보 가져오기
            fetch(`/api/members/${memberId}`)
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(errorData => {
                            const errorMessage = errorData.message || "회원 정보 조회 실패";
                            throw new Error(errorMessage);
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    setInputs(data.result); // 입력 값 설정
                    memberEditModal.style.display = "block"; // 모달 열기
                })
                .catch(error => {
                    alert(`${error.message}`);
                });
        }
    });

    // 모달 닫기 버튼
    document.getElementById("closeEditModalBtn").addEventListener("click", () => {
        memberEditModal.style.display = "none";
        resetInputs(); // 닫힐 때 초기화
    });

    // 저장 버튼 클릭 이벤트
    document.getElementById("saveEditMemberBtn").addEventListener("click", () => {
        const updatedMember = getInputValues(); // 현재 입력된 값 가져오기

        fetch(`/api/members/${updatedMember.memberId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedMember)
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        const errorMessage = errorData.message || "회원 정보 수정 실패";
                        throw new Error(errorMessage);
                    });
                }
                return response.json();
            })
            .then(() => {
                alert("회원 정보가 성공적으로 수정되었습니다.");
                memberEditModal.style.display = "none";

                // 목록 갱신
                window.location.reload();
            })
            .catch(error => {
                alert(`${error.message}`);
            });
    });
});
