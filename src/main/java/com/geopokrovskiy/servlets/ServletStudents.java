package com.geopokrovskiy.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geopokrovskiy.dto.ResponseResult;
import com.geopokrovskiy.model.Student;
import com.geopokrovskiy.repository.StudentRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/students")
public class ServletStudents extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * POST request
     * All parameters of this entity should be given in .json format
     * @param req
     * @param resp
     * @return
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseResult<Student> responseResult = new ResponseResult<>();
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        try (BufferedReader bufferedReader = new BufferedReader(req.getReader())){
            Student student = this.objectMapper.readValue(bufferedReader, Student.class);
            new StudentRepository().add(student);
            responseResult.setResult(true);
            responseResult.setData(student);
        } catch (NumberFormatException exception) {
            responseResult.setMessage("Incorrect format number");
            resp.setStatus(400);
        }
        this.objectMapper.writeValue(resp.getWriter(), responseResult);
    }

    /**
     * GET request
     * Getting data (by id and for everyone).
     * Either parameters are not accepted, or the entity id is accepted
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        if (req.getParameter("id") == null) {
            ResponseResult<List<Student>> responseResult = new ResponseResult<>();
            List<Student> studentList = new StudentRepository().getStudentList();
            responseResult.setResult(true);
            responseResult.setData(studentList);
            this.objectMapper.writeValue(resp.getWriter(), responseResult);
        } else {
            ResponseResult<Student> responseResult = new ResponseResult<>();
            try {
                long id = Long.parseLong(req.getParameter("id"));
                Student student = new StudentRepository().getStudent(id);
                if (student == null) {
                    responseResult.setMessage("Student does not exist!");
                } else {
                    responseResult.setResult(true);
                    responseResult.setData(student);
                }
            } catch (NumberFormatException exception) {
                responseResult.setMessage(exception.getMessage());
                resp.setStatus(400);
            }
            this.objectMapper.writeValue(resp.getWriter(), responseResult);
        }
    }

    /**
     * PUT request
     * All parameters of this entity are accepted in .json format
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        ResponseResult<Student> responseResult = new ResponseResult<>();
        try(BufferedReader bufferedReader = new BufferedReader(req.getReader())) {
            Student student = this.objectMapper.readValue(bufferedReader, Student.class);
            if(!new StudentRepository().update(student)){
                throw new IllegalArgumentException("Student does not exist!");
            }
            responseResult.setData(student);
            responseResult.setResult(true);
        } catch (Exception exception) {
            responseResult.setMessage("Incorrect number format or the student with the given id does not exist!");
            resp.setStatus(400);
        }
        this.objectMapper.writeValue(resp.getWriter(), responseResult);
    }

    /**
     * DELETE request
     * Entity id is accepted
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        ResponseResult<Student> responseResult = new ResponseResult<>();
        try {
            long id = Integer.parseInt(req.getParameter("id"));
            Student deleted = new StudentRepository().delete(id);
            if (deleted == null) {
                throw new IllegalArgumentException("Student does not exist!");
            }
            responseResult.setData(deleted);
            responseResult.setResult(true);
        } catch (Exception exception) {
            responseResult.setMessage("Incorrect number format");
        }
        this.objectMapper.writeValue(resp.getWriter(), responseResult);
        resp.setStatus(400);
    }


}
