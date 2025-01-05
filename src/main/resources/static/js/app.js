var mapContainer = document.getElementById('map');
var mapOption = {
    center: new kakao.maps.LatLng(37.2803, 127.0181),
    level: 6
};
var map = new kakao.maps.Map(mapContainer, mapOption);

// 범례에 따른 색상 설정
const statusColors = {
    "정상": "green",
    "오류": "red",
    "미접속": "yellow"
};

// sensors/detail 데이터를 LNB에 표시
function refreshSignalList() {
    fetch('/api/sensors/detail', {
        method: 'GET',
        headers: { Authorization: localStorage.getItem('Authorization') }
    })
        .then(response => response.json())
        .then(data => {
            const signalList = document.getElementById('signal-list');
            signalList.innerHTML = '';

            data.response.forEach(signal => {
                const card = document.createElement('div');
                card.className = 'signal-card';

                card.innerHTML = `
                    <div>${signal.sensorId}</div>
                    <div>${signal.address}</div>
                    <div>${signal.buttonClickCount}</div>
                    <div>${signal.locationGuideCount}</div>
                    <div>${signal.signalGuideCount}</div>
                    <div>${new Date(signal.createdAt).toLocaleString()}</div>
                    <div><span class="status-icon ${statusColors[signal.status]}"></span></div>
                `;
                signalList.appendChild(card);
            });
        })
        .catch(error => console.error('Error fetching signal list:', error));
    }

// sensors 데이터를 기반으로 지도에 마커 표시
function loadMapMarkers() {
    const token = localStorage.getItem('Authorization'); // 토큰 가져오기

    fetch('/api/sensors', {
        method: 'GET',
        headers: {
            Authorization: token,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(sensors => {
            sensors.response.forEach(sensor => {
                if (!sensor.latitude || !sensor.longitude) {
                    console.warn(`센서 ${sensor.sensorId}의 좌표가 없습니다.`);
                    return;
                }

                const position = new kakao.maps.LatLng(sensor.latitude, sensor.longitude);
                const markerImage = new kakao.maps.MarkerImage(
                    `/images/${statusColors[sensor.status]}-icon.png`,
                    new kakao.maps.Size(24, 24)
                );

                const marker = new kakao.maps.Marker({
                    position: position,
                    image: markerImage,
                    map: map
                });

                kakao.maps.event.addListener(marker, 'click', function () {
                    alert(`센서 ID: ${sensor.sensorId}\n상태: ${sensor.status}\n주소: ${sensor.address}`);
                });
            });
        })
        .catch(error => console.error('Error fetching sensor data:', error));
}

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', () => {
    refreshSignalList();
    loadMapMarkers();
});

function logout() {
            localStorage.removeItem('Authorization');
            alert('로그아웃 되었습니다. 로그인 페이지로 이동합니다.');
            window.location.href = '/auth/sign-in';
        }