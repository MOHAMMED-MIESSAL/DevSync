package com.devsync.service;

import com.devsync.entity.ReplacementRequest;
import com.devsync.entity.Task;
import com.devsync.entity.User;
import com.devsync.exceptions.ResourceNotFoundException;
import com.devsync.exceptions.ValidationException;
import com.devsync.repository.implementations.ReplacementRequestRepositoryImpl;
import com.devsync.repository.interfaces.ReplacementRequestRepository;

import java.util.List;
import java.util.Optional;

public class ReplacementRequestService {

    private final ReplacementRequestRepository replacementRequestRepository;
    private final TaskService taskService = new TaskService();

    public ReplacementRequestService() {
        this.replacementRequestRepository = new ReplacementRequestRepositoryImpl();

    }

    public void submitRequest(Task task, User requester) {
        ReplacementRequest request = new ReplacementRequest(task, requester, "PENDING");
        replacementRequestRepository.save(request);
    }

    public List<ReplacementRequest> getAllPendingRequests() {
        return replacementRequestRepository.findAllPending();
    }

    public void approveRequest(Long requestId, User manager, User newUser) {
        Optional<ReplacementRequest> requestOpt = replacementRequestRepository.findById(requestId);

        if (requestOpt.isEmpty()) {
            throw new ResourceNotFoundException("La demande de remplacement n'existe pas.");
        }

        ReplacementRequest request = requestOpt.get();
        if (!request.getStatus().equals("PENDING")) {
            throw new ValidationException("Cette demande a déjà été traitée.");
        }

        // Approuver et réaffecter la tâche
        Task task = request.getTask();
        task.setUserAffected(newUser); // Remplacer l'utilisateur assigné
        taskService.update(task,manager); // Mise à jour de la tâche

        // Mettre à jour la demande
        request.setStatus("APPROVED");
        replacementRequestRepository.update(request);
    }
}
