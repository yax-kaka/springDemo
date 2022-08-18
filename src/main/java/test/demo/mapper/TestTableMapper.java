package test.demo.mapper;

import test.demo.entity.TestTable;
import test.demo.vo.TestTableDTO;
import test.demo.vo.TestTableRequestVO;
import test.demo.vo.TestTableResponseVO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestTableMapper extends BaseMapper<TestTable> {
    @Select("<script>SELECT * from test_table where 1=1 " +
            "<if test = 'params.id != 0 and params.id != null'> and id > #{params.id}</if>" +
            "</script>")
    public IPage<TestTableResponseVO> selectPageVo(Page<TestTableResponseVO> page, @Param("params") TestTableRequestVO params);


}
