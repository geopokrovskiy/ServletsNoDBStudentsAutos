package com.geopokrovskiy.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geopokrovskiy.dto.ResponseResult;
import com.geopokrovskiy.model.Auto;
import com.geopokrovskiy.repository.AutoRepository;
import com.geopokrovskiy.repository.StudentRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/autos")
public class ServletAutos extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * POST request
     * All parameters of this entity are accepted without id
     *
     * @param req
     * @param resp
     * @return
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        String brand = req.getParameter("brand");
        String powerParam = req.getParameter("power");
        String yearParam = req.getParameter("year");
        String studentIdParam = req.getParameter("student_id");
        ResponseResult<Auto> responseResult = new ResponseResult<>();
        try {
            if (brand != null && powerParam != null && studentIdParam != null && yearParam != null) {
                int year = Integer.parseInt(yearParam);
                int power = Integer.parseInt(powerParam);
                long studentId = Long.parseLong(studentIdParam);
                if (!new StudentRepository().getStudentList().stream().anyMatch(student -> student.getId() == studentId)) {
                    responseResult.setMessage("The student with the given id does not exist!");
                } else {
                    Auto auto = new Auto(brand, power, year, studentId);
                    new AutoRepository().add(auto);
                    responseResult.setResult(true);
                    responseResult.setData(auto);
                }
            } else {
                responseResult.setMessage("Null parameters of the request");
            }
        } catch (NumberFormatException exception) {
            responseResult.setMessage("Incorrect number format");
        }
        this.objectMapper.writeValue(resp.getWriter(), responseResult);
    }

    /**
     * GET request
     * Getting data (by id and for everyone).
     * Either parameters are not accepted, or the entity id is accepted
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        if (req.getParameter("id") == null) {
            ResponseResult<List<Auto>> responseResult = new ResponseResult<>();
            List<Auto> autoList = new AutoRepository().getAutoList();
            responseResult.setData(autoList);
            responseResult.setResult(true);
            this.objectMapper.writeValue(resp.getWriter(), responseResult);
        } else {
            ResponseResult<Auto> responseResult = new ResponseResult<>();
            try {
                long id = Long.parseLong(req.getParameter("id"));
                Auto auto = new AutoRepository().getAuto(id);
                if (auto == null) {
                    throw new IllegalArgumentException("Auto does not exist!");
                } else {
                    responseResult.setResult(true);
                    responseResult.setData(auto);
                }
            } catch (NumberFormatException exception) {
                responseResult.setMessage("Incorrect number format");
            }
            this.objectMapper.writeValue(resp.getWriter(), responseResult);
        }
    }

    /**
     * PUT request
     * All parameters of this entity are accepted
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        ResponseResult<Auto> responseResult = new ResponseResult<>();
        try {
            String brand = req.getParameter("brand");
            String powerParam = req.getParameter("power");
            String yearParam = req.getParameter("year");
            String studentIdParam = req.getParameter("student_id");
            String idParam = req.getParameter("id");
            if (brand != null && powerParam != null && studentIdParam != null && yearParam != null && idParam != null) {
                int power = Integer.parseInt(powerParam);
                int year = Integer.parseInt(yearParam);
                long studentId = Long.parseLong(studentIdParam);
                long id = Long.parseLong(idParam);
                if (!new StudentRepository().getStudentList().stream().anyMatch(student -> student.getId() == studentId)) {
                    responseResult.setMessage("The student with the given id does not exist!");
                } else {
                    Auto auto = new Auto(id, brand, power, year, studentId);
                    if (!new AutoRepository().update(auto)) {
                        throw new IllegalArgumentException("Auto does not exist!");
                    }
                    responseResult.setData(auto);
                    responseResult.setResult(true);
                }
            } else {
                responseResult.setMessage("Null parameters of the request");
            }
        } catch (NumberFormatException exception) {
            responseResult.setMessage("Incorrect number format");
        }
        this.objectMapper.writeValue(resp.getWriter(), responseResult);
    }

    /**
     * DELETE request
     * Entity id is accepted
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        ResponseResult<Auto> responseResult = new ResponseResult<>();
        try {
            long id = Long.parseLong(req.getParameter("id"));
            Auto deleted = new AutoRepository().delete(id);
            if (deleted == null) {
                throw new IllegalArgumentException("Auto does not exist!");
            }
            responseResult.setResult(true);
            responseResult.setData(deleted);
        } catch (NumberFormatException exception) {
            responseResult.setMessage("Incorrect number format");
        }
        this.objectMapper.writeValue(resp.getWriter(), responseResult);
    }
}
