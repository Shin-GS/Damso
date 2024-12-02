document.addEventListener("DOMContentLoaded", () => {
    const memberAddModal = document.getElementById('memberAddModal');

    // 입력 필드 및 초기값 설정
    const inputs = {
        name: {
            element: document.getElementById('register-name'),
            defaultValue: ''
        },
        email: {
            element: document.getElementById('register-email'),
            defaultValue: ''
        },
        role: {
            element: document.getElementById('register-role'),
            defaultValue: 'USER'
        }
    };

    // 입력 값 초기화 함수
    const resetInputs = () => {
        Object.values(inputs).forEach(input => {
            input.element.value = input.defaultValue; // 초기값으로 설정
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

    // 모달 열기 버튼 클릭 이벤트
    document.getElementById('openModalBtn').addEventListener('click', () => {
        resetInputs(); // 입력 필드 초기화
        memberAddModal.style.display = 'block';
    });

    // 모달 닫기 버튼 클릭 이벤트
    document.getElementById('closeModalBtn').addEventListener('click', () => {
        memberAddModal.style.display = 'none';
    });

    // 회원 추가 버튼 클릭 이벤트
    document.getElementById('addMemberBtn').addEventListener('click', () => {
        const memberData = getInputValues();

        // 입력값 검증
        if (!memberData.name || !memberData.email || !memberData.role) {
            alert("모든 필드를 입력해주세요.");
            return;
        }

        // 서버에 데이터 전송
        fetch('/api/members', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(memberData)
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        const errorMessage = errorData.message || "회원 추가 실패";
                        throw new Error(errorMessage);
                    });
                }
                return response.json();
            })
            .then(() => {
                alert("회원이 성공적으로 추가되었습니다.");
                memberAddModal.style.display = 'none';

                // 회원 목록 갱신 - 외부에 정의된 loadMembers 함수 호출
                if (typeof loadMembers === "function") {
                    loadMembers(1); // 첫 페이지로 목록 갱신
                }
            })
            .catch(error => {
                alert(`${error.message}`);
            });
    });
});
