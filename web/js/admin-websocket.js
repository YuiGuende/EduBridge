// WebSocket connection for real-time notifications
let socket = null
let reconnectInterval = null
const bootstrap = window.bootstrap // Declare the bootstrap variable

function initWebSocket() {
    const protocol = window.location.protocol === "https:" ? "wss:" : "ws:"
    const wsUrl = `${protocol}//${window.location.host}/EduBridge/admin-notifications`;


    try {
        socket = new WebSocket(wsUrl)

        socket.onopen = (event) => {
            console.log("WebSocket connected")
            clearInterval(reconnectInterval)

            // Send authentication token or admin ID
            const adminId = getAdminId() // Implement this function
            if (adminId) {
                socket.send(
                        JSON.stringify({
                            type: "auth",
                            adminId: adminId,
                        }),
                        )
            }
        }

        socket.onmessage = (event) => {
            try {
                const notification = JSON.parse(event.data)
                handleNotification(notification)
            } catch (e) {
                console.error("Error parsing notification:", e)
            }
        }

        socket.onclose = (event) => {
            console.log("WebSocket disconnected")
            // Attempt to reconnect every 5 seconds
            reconnectInterval = setInterval(initWebSocket, 5000)
        }

        socket.onerror = (error) => {
            console.error("WebSocket error:", error)
        }
    } catch (error) {
        console.error("Failed to create WebSocket connection:", error)
        // Fallback to polling
        startPolling()
    }
}

function handleNotification(notification) {
    console.log("Received notification:", notification)

    switch (notification.type) {
        case "COURSE_PENDING_APPROVAL":
            showToast(
                    "New Course Pending Approval",
                    `Course "${notification.courseTitle}" by ${notification.instructorName} needs review`,
                    "warning",
                    `/admin/courses?action=view&id=${notification.courseId}`,
                    )
            updatePendingCount()
            break

        case "NEW_REPORT":
            showToast(
                    "New Report Received",
                    `New ${notification.reportType} report received`,
                    "danger",
                    `/admin/reports?action=view&id=${notification.reportId}`,
                    )
            updateReportCount()
            break

        case "INSTRUCTOR_REQUEST":
            showToast(
                    "New Instructor Application",
                    `${notification.userName} applied to become an instructor`,
                    "info",
                    `/admin/instructors?action=view&id=${notification.userId}`,
                    )
            break

        default:
            showToast("Notification", notification.message, "info")
    }

    // Play notification sound
    playNotificationSound()
}

function showToast(title, message, type = "info", actionUrl = null) {
    const toastContainer = document.getElementById("toastContainer")
    if (!toastContainer)
        return

    const toastId = "toast-" + Date.now()
    const bgClass =
            {
                success: "bg-success",
                warning: "bg-warning",
                danger: "bg-danger",
                info: "bg-info",
            }[type] || "bg-info"

    const actionButton = actionUrl
            ? `<button type="button" class="btn btn-sm btn-light ms-2" onclick="window.location.href='${actionUrl}'">
            View
        </button>`
            : ""

    const toastHtml = `
        <div id="${toastId}" class="toast align-items-center text-white ${bgClass} border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">
                    <strong>${title}</strong><br>
                    ${message}
                    ${actionButton}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    `

    toastContainer.insertAdjacentHTML("beforeend", toastHtml)

    const toastElement = document.getElementById(toastId)
    const toast = new bootstrap.Toast(toastElement, {
        autohide: true,
        delay: 8000,
    })

    toast.show()

    // Remove toast element after it's hidden
    toastElement.addEventListener("hidden.bs.toast", () => {
        toastElement.remove()
    })
}

function updatePendingCount() {
    // Update pending course approval count in UI
    fetch("/admin/api/pending-count")
            .then((response) => response.json())
            .then((data) => {
                const countElement = document.querySelector(".pending-count")
                if (countElement) {
                    countElement.textContent = data.count
                    if (data.count > 0) {
                        countElement.classList.add("badge", "bg-warning")
                    }
                }
            })
            .catch((error) => console.error("Error updating pending count:", error))
}

function updateReportCount() {
    // Update report count in UI
    fetch("/admin/api/report-count")
            .then((response) => response.json())
            .then((data) => {
                const countElement = document.querySelector(".report-count")
                if (countElement) {
                    countElement.textContent = data.count
                    if (data.count > 0) {
                        countElement.classList.add("badge", "bg-danger")
                    }
                }
            })
            .catch((error) => console.error("Error updating report count:", error))
}

function playNotificationSound() {
    // Play a subtle notification sound
    try {
        const audio = new Audio("/sounds/notification.mp3")
        audio.volume = 0.3
        audio.play().catch((e) => {
            // Ignore audio play errors (user interaction required)
            console.log("Audio play prevented:", e.message)
        })
    } catch (error) {
        console.log("Audio not available")
    }
}

function getAdminId() {
    // Get admin ID from session or local storage
    // This should be implemented based on your authentication system
//  return sessionStorage.getItem("user") || localStorage.getItem("adminId")
    return "999";
}

function startPolling() {
    // Fallback polling mechanism if WebSocket fails
    setInterval(() => {
        fetch("/admin/api/notifications")
                .then((response) => response.json())
                .then((notifications) => {
                    notifications.forEach(handleNotification)
                })
                .catch((error) => console.error("Polling error:", error))
    }, 30000) // Poll every 30 seconds
}

// Initialize WebSocket when page loads
document.addEventListener("DOMContentLoaded", () => {
    initWebSocket()
})

// Cleanup on page unload
window.addEventListener("beforeunload", () => {
    if (socket) {
        socket.close()
    }
    if (reconnectInterval) {
        clearInterval(reconnectInterval)
    }
})
