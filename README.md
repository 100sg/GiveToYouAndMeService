## GiveToYouAndMe Project 

> 문제해결 설명
##1. 뿌리기시  
    1. 분배되는 금액에는 0원이 있지 않도록 설정
    2. 설정한 금액에서 랜덤한 돈이 차감되어 지는 형태로 설정한 인원수 만큼 돈 분배가 
       이뤄지지 않을 수 있어서 분배 후 남은 금액은 남은 인원보다 크거나 같도록 설정
    3. 뿌리는 금액은 설정된 인원보다 같거나 클때에만 API 호출 가능
    4. 랜덤 함수 특성상 범위가 점점 좁혀지기 때문에 줍기시 먼저 줍는 사람이 항상 큰 금액을
       가져갈 수 있으므로 분배된 금액을 랜덤하게 저장
    5. 토큰은 영문자+숫자 조합 62자리를 통해 랜덤추출로 조합하여 3자리 생성
     
###2. 줍기시
    1. 줍기 가능한 리스트를 디비에 저장
    2. 줍기 상태를 체크하는 컬럼값이 존재
    3. 루프 최소화를 위해 상태값에 따른 리스트를 만들어서 줍기 리스트를 소비함
    
###3. 정보조회시
    1. 뿌리기 정보와 함께 줍기 상태값을 통해 줍기가 이뤄진 상제정보 리스트를 제공
   
