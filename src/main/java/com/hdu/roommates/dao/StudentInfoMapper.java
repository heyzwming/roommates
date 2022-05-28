package com.hdu.roommates.dao;

import com.hdu.roommates.entity.StudentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: TODO
 * @Author xyzhen
 */
@Mapper
public interface StudentInfoMapper {
    StudentInfo selectById(int studentId);

    // select出所有没有找到宿舍的学生的信息    // 为了分页 需要参数
    List<StudentInfo> selectStudentInfo(int studentId, int offset, int limit);

    // 根据学生名字搜索该学生  可能存在重名，返回list
    List<StudentInfo> selectStudentByName(String name, int offset, int limit);

    // 检索单个学生 TODO:暂时不考虑多学生的情况
    StudentInfo selectTheStudentByName(String name);
    // 根据地域搜索学生
    List<StudentInfo> selectStudentByLocation(String location, int offset, int limit);

    // 查询行数
    int selectStudentRows(@Param("studentId") int studentId );  // studentId用于查询"我的室友"  //

    // ---------------------------TODO:实现进度

    // TODO: 根据content模糊搜索 like
    List<StudentInfo> selectStudentByKey(String key);  // 传入关键词，在content中搜关键词

}

