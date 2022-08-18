package test.demo.service.impl;

import test.demo.entity.TestTable;
import test.demo.vo.TestTableDTO;
import test.demo.vo.TestTableRequestVO;
import test.demo.vo.TestTableResponseVO;
import test.demo.mapper.TestTableMapper;
import test.demo.service.LocalTestService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class LocalTestServiceImpl extends ServiceImpl<TestTableMapper,TestTable> implements LocalTestService {
    @Override
    public IPage<TestTableResponseVO> pageList(TestTableRequestVO requestVO){
        Page<TestTableResponseVO> page = new Page<>(1, 20);
        page.addOrder(OrderItem.desc("id"));
        IPage<TestTableResponseVO> iPage = baseMapper.selectPageVo(page, requestVO);
        return iPage;
    }

    @Override
    public boolean addRecord(TestTableDTO testTableDTO) {
        log.warn(String.valueOf(testTableDTO));
        TestTable testTable = new TestTable();
        testTable.setName(testTableDTO.getName());
        testTable.setTestName(testTableDTO.getTestName());
        testTable.setIntroduction(testTableDTO.getIntroduction());
        return save(testTable);
    }
}
