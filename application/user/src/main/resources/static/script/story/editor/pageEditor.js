async function initializePageEditor() {
    const container = document.getElementById("sortable-page-container");
    const sortableList = document.getElementById("sortable-page-list");
    if (!container || !sortableList) return;

    const sortable = new Sortable(sortableList, {
        animation: 150,
        ghostClass: "bg-blue-100",
        onEnd: async (evt) => {
            try {
                await sendReorderRequest();
            } catch (error) {
                console.error("Reorder request failed:", error);
            }
        },
    });

    async function sendReorderRequest() {
        const listItems = [...sortableList.querySelectorAll("[draggable]")];
        const pageOrders = listItems.map((item, index) => ({
            storyPageId: item.dataset.id,
            order: index,
        }));

        const storyId = container.getAttribute("data-story-id");
        try {
            const response = await fetch(`/api/stories/edit/${storyId}/pages/reorder`, {
                method: "PUT",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({pageOrders}),
            });

            if (!response.ok) {
                const errorDetails = await response.json();
                console.error("순서 업데이트 중 오류:", errorDetails);
            }
        } catch (error) {
            console.error("서버 요청 실패:", error);
            throw error;
        }
    }
}
