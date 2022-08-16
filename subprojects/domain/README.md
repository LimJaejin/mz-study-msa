Domain Layer 
===
## 개요
+ 핵심/세부 비즈니스 로직 서비스 제공
+ 도메일 모델 패턴 구현
+ 다른 레이어 의존성 없음
+ JPA 엔터티, Repository 인터페이스, Client 인터페이스
___
## 주요 클래스
### DomainService
+ 비즈니스 로직을 처리합니다.
```
@Slf4j
@RequiredArgsConstructor
@Service
public class SampleDomainService {

    private final SampleMapper sampleMapper;
    private final SampleMemberMapper sampleMemberMapper;
    private final SampleMemberMappMapper sampleMemberMappMapper;
    private final SampleRepository sampleRepository;

    /**
     * 샘플 멤버를 등록한다.
     *
     * @param dto 샘플 멤버 Dto
     * @return SampleMemberDto
     */
    public SampleMemberDto create(SampleMemberDto dto) {
        try {
            dto.setRegDate(LocalDateTime.now());

            // dto -> entity 변환
            SampleMember entity = this.sampleMemberMapper.toEntity(dto);

            // entity DB 저장
            this.sampleRepository.create(entity);

            // entity -> dto 변환
            return this.sampleMemberMapper.toDto(entity);
        }
        catch (Exception e) {
            throw new ServiceException(OuterResponseType.FAIL_002, e);
        }
    }

    /**
     * 샘플 멤버 매핑 데이터를 등록한다.
     *
     * @param sampleMemberId 샘플 멤버 아이디
     * @param mappDto 샘플 멤버 매핑 Dto
     * @return SampleMemberMappDto
     */
    public SampleMemberMappDto create(String sampleMemberId, SampleMemberMappDto mappDto) {
        try {
            mappDto.setSampleMemberId(sampleMemberId);
            mappDto.setMappingDate(LocalDateTime.now());

            SampleMemberMapp entity = this.sampleMemberMappMapper.toEntity(mappDto);

            this.sampleRepository.create(entity);

            return this.sampleMemberMappMapper.toDto(entity);
        }
        catch (Exception e) {
            throw new ServiceException(OuterResponseType.FAIL_003, e);
        }
    }

    /**
     * 샘플 멤버 & 샘플 멤버 매핑 목록을 조회한다.
     *
     * @param queryConditonDto 쿼리 검색 조건 Dto
     * @return List<SampleDto>
     */
    public List<SampleDto> getSamples(SampleQueryConditonDto queryConditonDto) {
        try {
            // 검색 조건 체크
            this.checkQueryCondition(queryConditonDto);
            
            // entity -> dto 변환 및 dto 속성 값 변환 처리
            return this.sampleRepository.findSamples(queryConditonDto).stream()
                    .map(this.sampleMapper::toDto)
                    .peek(s -> {
                        String name = s.getName();
                        s.setName(StringUtils.left(name, 2) + "X");

                        String email = s.getEmail();
                        String[] arr = email.split("@", -1);
                        s.setEmail(StringUtils.left(arr[0], 3) + "XXX@" + arr[1]);
                    })
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new ServiceException(OuterResponseType.FAIL_004, e);
        }
    }

    /**
     * 검색 조건 체크
     *
     * @param queryConditonDto
     */
    private void checkQueryCondition(SampleQueryConditonDto queryConditonDto) {
        if (Objects.isNull(queryConditonDto)
                || (
                StringUtils.isBlank(queryConditonDto.getName())
                        && StringUtils.isBlank(queryConditonDto.getEmail())
                        && StringUtils.isBlank(queryConditonDto.getSaId())
                        && StringUtils.isBlank(queryConditonDto.getStbMac())
            )
        ) {
            throw new ServiceException("검색 조건없는 조회는 불가합니다. 검색 조건을 입력해주세요.");
        }
    }
}
```
___
### Entity
+ DB 테이블과 1:1로 매칭되는 클래스입니다.
+ getter, 기본 생성자, @Id 가 기본적으로 포함되어 있어야 합니다.
+ setter는 필요 시, 필드 레벨에 선택적으로 추가합니다. 클래스 레벨에는 추가하지 않습니다.
```
@Getter
@Entity
@Table(name = "imcsuser.sample_member")
public class SampleMember implements Serializable {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;
}
```
___
### Mapper(dto ↔︎ entity)
+ MapStruct를 이용한 인터페이스를 생성하여 dto와 entity간 데이터를 변환합니다.
```
@Mapper(config = MapstructConfig.class)
public interface SampleMemberMapper {

    SampleMember toEntity(SampleMemberDto dto);

    SampleMemberDto toDto(SampleMember entity);
}
```
___
### CustomException
+ application 레이어로 특정 객체나 값이 아닌 오류를 로깅 후 던져야 할 때, 오류를 catch하여 RuntimeException을 상속한 CustomException을 throw합니다.
```
public class ServiceException extends RuntimeException {

    @Getter
    private OuterResponseType outerResponseType;

    @Getter
    private String errorMessage;

    @Getter
    private CommonResponseDto responseDto;

    public ServiceException(OuterResponseType outerResponseType, Throwable e) {
        super(e);
        this.outerResponseType = outerResponseType;
    }

    public ServiceException(String errorMessage, Throwable e) {
        super(e);
        this.errorMessage = errorMessage;
    }

    public ServiceException(CommonResponseDto responseDto, Throwable e) {
        super(e);
        this.responseDto = responseDto;
    }

    public ServiceException(OuterResponseType outerResponseType) {
        this.outerResponseType = outerResponseType;
    }

    public ServiceException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
```
### Repository(인터페이스)
+ 해당 MSA 서비스의 DB 데이터 처리를 담당합니다.
+ 구현체는 infrastructure 레이어에서 작성합니다.
```
public interface SampleRepository {
    SampleMember create(SampleMember entity);
    SampleMemberMapp create(SampleMemberMapp entity);
}
```
___
### Client(인터페이스)
+ 외부 시스템(내부 MSA 서비스)과의 연동을 처리합니다.
+ 구현체는 infrastructure 레이어에 작성합니다.
```
public interface SubscriberInfoClient {
    InnerResponseDto<SubscriberInfoDto> findBySaIdAndStbMac(String saId, String stbMac);
}
```
___
## 일반적인 domain 레이어 개발
+ ~~조인 및 통계성 쿼리의 데이터를 EntityManager로 조회할 경우 entity 클래스가 아니면 오류를 발생시킵니다. 이를 방지하기 위해 유사 entity(EntityDto)로 이를 받아 처리하도록 합니다.~~
  + ~~이때 EntityDto 클래스는 @Table 어노테이션을 선언하지 않아야 하며 lombok의 @Setter 또는 setter의 사용을 금지해야 합니다.~~
+ Entity ↔︎ Dto간 매핑 간소화를 위해 mapstruct를 이용합니다.
+ 클래스 및 메소드 상단에 @Transactional 어노테이션을 선언하지 않습니다.
  + 트랜잭션은 application 레이어에서 비즈니스 로직 단위로 선언하여 관리하도록 합니다.
+ 메소드 실패 시 오류 메시지를 정의합니다.
+ 가능한 메소드 내부에서 try ~ catch로 에러를 잡아 custom exception으로 추상화하여 application 레이어로 throw하도록 합니다.
+ 메소드의 리턴 값이 캐싱 가능한 데이터라면 메소드 상단에 @Cachable 어노테이션을 추가하여 캐싱되도록 처리합니다.
  + 캐싱 가능한 데이터: 자주 사용되며 드물게 갱신되는 데이터

