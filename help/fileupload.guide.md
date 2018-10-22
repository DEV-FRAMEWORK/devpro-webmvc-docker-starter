# 파일 업로드/다운로드 기능 가이드
---
*  파일 업로드/ 다운로드 관련 파일 설명
파일 업/다운로드 기능은 게시판 샘플로 구현함.   
sampleboard 파일들 참조하여 기능구현하면됨.
* 파일업로드 구현시 보안 중요 사항  
  1. 파일 저장 경로 체크 로직
  2. 저장 파일 명 랜덤생성 및 저장파일 직접제어 불가
  3. 실제파일명 저장파일명 DB관리
  4. 파일 확장자 체크(업로드 허용확장자)
  5. 파일 업로드 사이즈 체크 (업로드 허용 사이즈)

### 샘플파일설명
* FileUtil.java : 파일업로드 다운로드 메소드 구현
~~~java
  @Component("fileUtils")
    public class FileUtil {
	
    @Value("${server.filePath}")
    private static String filePath ;
    
	@Value("${server.fileExt}")
    private String fileExt ;
	
	@Value("${server.file.maxsize}")
    private String FileMaxSize ;

	@Value("${server.filePath}")
    public void setFilePath(String Path) {
		FileUtil.filePath = Path;
    }
	
	/**
     * 파일 저장
     *
     * @param 
     * @return
     */

	public List<Map<String,Object>> parseInsertFileInfo(SampleBoardDomain sampleBoardDomain,   HttpServletRequest request, String storePath) throws Exception{
		
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
    	Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
    	
    	MultipartFile multipartFile = null;
    	String originalFileName = null;
    //	String originalFileExtension = null;
    	String storedFileName = null;
    	String storedFilePath = null;
    	
    	// 파일 저장경로 설정.
    	if ("".equals(storePath) || storePath == null) {
    		storedFilePath = filePath;  // property 처리...
		} else {
			storedFilePath = storePath; // property 처리...
		}
    	log.debug("filepath======>"+storedFilePath );
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null; 
        
        //게시판.idx 
        int boardIdx = sampleBoardDomain.getIdx();
        
        //filePathBlackList() 저장경로 변경체크.
        File file = new File(filePathBlackList(storedFilePath));   
        
        //파일 저장경로 생성..
        if (!file.exists() || file.isFile()) {
			//시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
			
			if (file.mkdirs()){
				log.debug("[file.mkdirs] saveFolder : Creation Success ");
			}else{
				log.error("[file.mkdirs] saveFolder : Creation Fail ");
			}
		} 
       
        
        while(iterator.hasNext()){
  
        	multipartFile = multipartHttpServletRequest.getFile(iterator.next());
        	if(multipartFile.isEmpty() == false){
        		
        		//파일명 획득.
        		originalFileName = multipartFile.getOriginalFilename();
        		//--------------------------------------
    			// 원 파일명이 없는 경우 처리
    			// (첨부가 되지 않은 input file type)
    			//--------------------------------------
    			if ("".equals(originalFileName)) {
    				continue;
    			}
    			////------------------------------------
    			
    			//파일 확장자 및 사이즈 체크 ...(확장자리스트, 업로드 사이즈 property 참조.)
    			validateFile(multipartFile, "file"); 
    			
        		//파일의 저장파일명 랜덤생성.
        		storedFileName = CommonUtils.getRandomString()  ; 
        		
        		//파일생성.
        		file = new File(filePathBlackList(storedFilePath + storedFileName));
        		
        		log.debug("filename===>"+storedFilePath + storedFileName);
        		
        		//파일 저장.
        		multipartFile.transferTo(file);
        		
        		//파일 정보 DB 저장 위래 정보 return.
        		listMap = new HashMap<String,Object>();
        		listMap.put("boardIdx", boardIdx);
        		listMap.put("originalFileName", originalFileName);
        		listMap.put("storedFileName", storedFileName);
        		listMap.put("fileSize", multipartFile.getSize());
        		list.add(listMap);
        	}
        }
		return list;
	}
	
	
	public static  void downloadFile(String storedFileName, String originalFileName, HttpServletResponse res) throws Exception {
		

		byte fileByte[] = FileCopyUtils.copyToByteArray(new File(filePathBlackList(filePath+storedFileName)));
		res.setContentType("application/x-msdownload");
		res.setContentLength(fileByte.length);
		res.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";");
		res.setHeader("Content-Transfer-Encoding", "binary");
		res.getOutputStream().write(fileByte);
	     
		res.getOutputStream().flush();
		res.getOutputStream().close();
		
	}
	
	
	public static String filePathBlackList(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\

		return returnValue;
	}
	
	
	/**
     * 파일의 확장자 추출
     *
     * @param fname
     * @return
     */

    public static String getFileExt(String fname) {

        if (!fname.equals("")) {

            int lstIn = fname.lastIndexOf('.');

            String ext = fname.substring(lstIn + 1);

            return ext.toLowerCase();

        } else {

            return "";

        }

    }
    
	/**
     * 파일 확장자 및 사이즈 검사
     * @param MultipartFile
     * @return
     */

    private void validateFile(MultipartFile file, String type)  {

    	log.debug("file ori name, {} 파일체크" + type );
    	log.debug("file ori name, {} 파일확장자체크." + fileExt );
    	log.debug("file ori name, {} 파일사이즈체크." + FileMaxSize );
    	
        if("file".equals(type)){

        	log.debug("file ori name, {} 파일체크");
         

            String fileExt = getFileExt(file.getOriginalFilename());
            String originalFileName ;

            try {

                originalFileName = new String(file.getOriginalFilename().getBytes("8859_1"),"UTF-8");

            } catch(Exception e) {

                originalFileName = file.getOriginalFilename();

            }
            log.debug("file ori name, {} 파일명 : ===>" + originalFileName );  
            

            if( (originalFileName.indexOf("\0")>-1) || (originalFileName.indexOf(";")>-1) || (originalFileName.indexOf("./")>-1) || (originalFileName.indexOf(".\\")>-1)) {
            	//예외처리 또는 에러처리 정의.
            	// ..throw new Exception("file ext check error 1");
            }

            String[] arrExt = fileExt.split(",");

            boolean isValidExt = false;

            for(String ext : arrExt ) {

                if( ext.trim().equals(fileExt.toLowerCase())) {

                    isValidExt = true;

                    log.debug("isValidExt0 ==== |{}|"+isValidExt);

                    break;

                }

            }

            if( !isValidExt ) {

            	log.debug("isValidExt2 ==== |{}|"+isValidExt);
               //예외처리 또는 에러처리 정의.
               //.. throw new Exception("file ext check error 2");

            }

            if( file.getSize()/1024/1024 > Double.parseDouble(FileMaxSize) ) {
            	//예외처리 또는 에러처리 정의.
               //.. throw new Exception("file size check error");

            }
        }
        
    }
}
~~~

* CommonUtils.java : 파일명 랜덤생성

~~~java
public class CommonUtils {
    Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static String getRandomString(){ //파일명 랜덤생성 메소드..
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
~~~

* 파일 스키마
    * schema.sql  
    
~~~sql
  CREATE TABLE SAMPLE_FILE
    (
    IDX   NUMBER,
    BOARD_IDX NUMBER NOT NULL,
    ORIGINAL_FILE_NAME VARCHAR2(260 BYTE) NOT NULL,
    STORED_FILE_NAME VARCHAR2(36 BYTE) NOT NULL,
    FILE_SIZE NUMBER,
    CREA_DTM  DATE DEFAULT SYSDATE NOT NULL,
    CREA_ID   VARCHAR2(30 BYTE) NOT NULL,
    DEL_GB    VARCHAR2(1 BYTE) DEFAULT 'N' NOT NULL,
    PRIMARY KEY (IDX)
    );
~~~  

* data.sql (초기 데이타 insert )
    필요시 초기 데이타 입력 구문
