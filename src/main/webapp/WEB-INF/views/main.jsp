<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Osan Smart Monitoring</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=702d6a0d5da5bb35bb8cb9c9ed0bab44"></script>
    <script src="${pageContext.request.contextPath}/js/app.js" defer></script>
</head>
<body>
    <header>
        <div class="gnb">
            <div class="logo">Osan 스마트 음향 신호기 모니터링</div>
            <div class="logout">
                <button onclick="logout()">로그아웃</button>
            </div>
        </div>
    </header>
    <main>
        <aside class="lnb">
            <div class="lnb-header">
                <h2>신호기 목록</h2>
                <button class="refresh-button" onclick="refreshSignalList()">🔄</button>
            </div>
            <div class="signal-index">
                <div>ID</div>
                <div>주소</div>
                <div>버튼 클릭</div>
                <div>위치 안내</div>
                <div>신호 안내</div>
                <div>생성일</div>
                <div>상태</div>
            </div>
            <div id="signal-list" class="signal-list">
                <!-- 신호기 목록 -->
            </div>
        </aside>
        <section class="map-container">
            <!-- 범례 -->
            <div class="legend">
                <h3>범례</h3>
                <ul>
                    <li><span class="legend-circle green"></span> 정상</li>
                    <li><span class="legend-circle red"></span> 오류</li>
                    <li><span class="legend-circle yellow"></span> 미접속</li>
                </ul>
            </div>
            <!-- 지도 -->
            <div id="map" class="map"></div>
        </section>
    </main>
</body>
</html>