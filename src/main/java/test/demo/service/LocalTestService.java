package test.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import test.demo.entity.TestTable;
import test.demo.vo.TestTableDTO;
import test.demo.vo.TestTableRequestVO;
import test.demo.vo.TestTableResponseVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LocalTestService extends IService<TestTable> {

    IPage<TestTableResponseVO> pageList(TestTableRequestVO requestVO);

    boolean addRecord(TestTableDTO testTableDTO);
}
